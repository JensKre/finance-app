from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from fastapi.staticfiles import StaticFiles
from fastapi.responses import HTMLResponse
from typing import List
import json
import os
import uuid
from models import FinanceRecord, FinanceRecordCreate, BudgetRecord, BudgetRecordCreate

app = FastAPI(title="Couples Finance Tracker API")

# Enable CORS for frontend
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # For local development
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DATA_FILE = os.path.join(BASE_DIR, "data.json")

def read_data():
    if not os.path.exists(DATA_FILE):
        return {"records": [], "budget_records": []}
    with open(DATA_FILE, "r") as f:
        try:
            data = json.load(f)
            if "budget_records" not in data:
                data["budget_records"] = []
            return data
        except json.JSONDecodeError:
            return {"records": [], "budget_records": []}

def write_data(data):
    with open(DATA_FILE, "w") as f:
        json.dump(data, f, indent=4)

@app.get("/records", response_model=List[FinanceRecord])
def get_records():
    data = read_data()
    return data.get("records", [])

@app.post("/records", response_model=FinanceRecord)
def create_record(record: FinanceRecordCreate):
    data = read_data()
    new_record = record.dict()
    new_record["id"] = str(uuid.uuid4())
    # Convert date to string for JSON serialization
    new_record["entry_date"] = new_record["entry_date"].isoformat()
    
    data["records"].append(new_record)
    write_data(data)
    
    return new_record

@app.delete("/records/{record_id}")
def delete_record(record_id: str):
    data = read_data()
    records = data.get("records", [])
    
    filtered_records = [r for r in records if r["id"] != record_id]
    
    if len(filtered_records) == len(records):
        raise HTTPException(status_code=404, detail="Record not found")
        
    data["records"] = filtered_records
    write_data(data)
    return {"message": "Record deleted successfully"}

@app.get("/institutes", response_model=List[str])
def get_institutes():
    data = read_data()
    return data.get("institutes", ["Sparkasse", "Trade Republic", "Binance"])

@app.post("/institutes", response_model=List[str])
def update_institutes(institutes: List[str]):
    data = read_data()
    data["institutes"] = institutes
    write_data(data)
    return institutes

@app.get("/categories", response_model=List[str])
def get_categories():
    data = read_data()
    return data.get("categories", ["Girokonto", "Tagesgeld", "ETF", "Gold", "Krypto"])

@app.post("/categories", response_model=List[str])
def update_categories(categories: List[str]):
    data = read_data()
    data["categories"] = categories
    write_data(data)
    return categories

# Production: Serve Frontend
STATIC_DIR = os.path.join(BASE_DIR, "static")
if os.path.exists(STATIC_DIR):
    app.mount("/assets", StaticFiles(directory=os.path.join(STATIC_DIR, "assets")), name="assets")
    

@app.get("/budget", response_model=List[BudgetRecord])
def get_budget_records():
    data = read_data()
    return data.get("budget_records", [])

@app.post("/budget", response_model=List[BudgetRecord])
def create_budget_records(records: List[BudgetRecordCreate]):
    data = read_data()
    new_records = []
    for r in records:
        new_record = r.dict()
        new_record["id"] = str(uuid.uuid4())
        new_records.append(new_record)
    
    data["budget_records"] = data.get("budget_records", []) + new_records
    write_data(data)
    return new_records

@app.delete("/budget")
def clear_budget():
    data = read_data()
    data["budget_records"] = []
    write_data(data)
    return {"message": "Budget cleared"}

@app.put("/records/{record_id}", response_model=FinanceRecord)
def update_record(record_id: str, record: FinanceRecordCreate):
    data = read_data()
    records = data.get("records", [])
    
    for idx, r in enumerate(records):
        if r["id"] == record_id:
            updated_record = record.dict()
            updated_record["id"] = record_id
            updated_record["entry_date"] = updated_record["entry_date"].isoformat()
            records[idx] = updated_record
            write_data(data)
            return updated_record
            
    raise HTTPException(status_code=404, detail="Record not found")

if os.path.exists(STATIC_DIR):
    @app.get("/{full_path:path}")
    async def serve_frontend(full_path: str):
        # Serve index.html for any route not starting with API prefixes
        if not full_path.startswith(("records", "institutes", "categories", "budget")):
            index_path = os.path.join(STATIC_DIR, "index.html")
            if os.path.exists(index_path):
                with open(index_path, "r") as f:
                    return HTMLResponse(content=f.read())
        raise HTTPException(status_code=404)
