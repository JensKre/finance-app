const API_URL = import.meta.env.PROD ? '' : 'http://localhost:8000';

export const fetchRecords = async () => {
  const response = await fetch(`${API_URL}/records`);
  if (!response.ok) throw new Error('Failed to fetch records');
  return response.json();
};

export const createRecord = async (record) => {
  const response = await fetch(`${API_URL}/records`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(record),
  });
  if (!response.ok) throw new Error('Failed to create record');
  return response.json();
};

export const updateRecord = async (id, record) => {
  const response = await fetch(`${API_URL}/records/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(record),
  });
  if (!response.ok) throw new Error('Failed to update record');
  return response.json();
};

export const deleteRecord = async (id) => {
  const response = await fetch(`${API_URL}/records/${id}`, {
    method: 'DELETE',
  });
  if (!response.ok) throw new Error('Failed to delete record');
  return response.json();
};

export const fetchInstitutes = async () => {
  const response = await fetch(`${API_URL}/institutes`);
  if (!response.ok) throw new Error('Failed to fetch institutes');
  return response.json();
};

export const updateInstitutes = async (institutes) => {
  const response = await fetch(`${API_URL}/institutes`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(institutes),
  });
  if (!response.ok) throw new Error('Failed to update institutes');
  return response.json();
};

export const fetchCategories = async () => {
  const response = await fetch(`${API_URL}/categories`);
  if (!response.ok) throw new Error('Failed to fetch categories');
  return response.json();
};

export const updateCategories = async (categories) => {
  const response = await fetch(`${API_URL}/categories`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(categories),
  });
  if (!response.ok) throw new Error('Failed to update categories');
  return response.json();
};

export const fetchBudgetRecords = async () => {
  const response = await fetch(`${API_URL}/budget`);
  if (!response.ok) throw new Error('Failed to fetch budget records');
  return response.json();
};

export const createBudgetRecords = async (records) => {
  const response = await fetch(`${API_URL}/budget`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(records),
  });
  if (!response.ok) throw new Error('Failed to create budget records');
  return response.json();
};

export const clearBudget = async () => {
  const response = await fetch(`${API_URL}/budget`, {
    method: 'DELETE',
  });
  if (!response.ok) throw new Error('Failed to clear budget');
  return response.json();
};
