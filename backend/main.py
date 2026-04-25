from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from typing import List
import json
import os
import uuid
from models import FinanceRecord, FinanceRecordCreate

app = FastAPI(title="Couples Finance Tracker API")

# Enable CORS for frontend
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # For local development
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

DATA_FILE = "data.json"

def read_data():
    if not os.path.exists(DATA_FILE):
        return {"records": []}
    with open(DATA_FILE, "r") as f:
        try:
            return json.load(f)
        except json.JSONDecodeError:
            return {"records": []}

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
