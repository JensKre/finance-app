from fastapi.testclient import TestClient
from main import app
import os
import json

client = TestClient(app)

def test_read_main():
    response = client.get("/records")
    assert response.status_code == 200
    assert isinstance(response.json(), list)

def test_create_record():
    payload = {
        "person": "Jens",
        "institution": "Test Bank",
        "category": "Girokonto",
        "amount": 123.45,
        "entry_date": "2026-04-25"
    }
    response = client.post("/records", json=payload)
    assert response.status_code == 200
    data = response.json()
    assert data["institution"] == "Test Bank"
    assert data["amount"] == 123.45
    assert "id" in data

def test_delete_record():
    # First create a record
    payload = {
        "person": "Annika",
        "institution": "Delete Me",
        "category": "Tagesgeld",
        "amount": 100.0,
        "entry_date": "2026-04-25"
    }
    create_res = client.post("/records", json=payload)
    record_id = create_res.json()["id"]
    
    # Then delete it
    response = client.delete(f"/records/{record_id}")
    assert response.status_code == 200
    assert response.json()["message"] == "Record deleted successfully"
