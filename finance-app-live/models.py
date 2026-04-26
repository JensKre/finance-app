from pydantic import BaseModel, Field
from typing import Optional
from datetime import date

class FinanceRecord(BaseModel):
    id: Optional[str] = None
    person: str  # "Jens" or "Annika"
    institution: str
    category: str
    amount: float
    entry_date: date

class FinanceRecordCreate(BaseModel):
    person: str
    institution: str
    category: str
    amount: float
    entry_date: date

class ForecastParameters(BaseModel):
    monthly_savings: float
    yearly_growth_percentage: float
