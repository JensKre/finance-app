import React, { useState, useEffect, useMemo } from 'react';
import useStore from './store';
import { 
  Plus, Trash2, Edit2, TrendingUp, Wallet, PieChart as PieChartIcon, 
  Download, Upload, X, CheckCircle, AlertCircle, Settings as SettingsIcon 
} from 'lucide-react';
import { 
  PieChart, Pie, Cell, ResponsiveContainer, Tooltip as RechartsTooltip, 
  LineChart, Line, XAxis, YAxis, CartesianGrid, Legend, BarChart, Bar, ReferenceLine 
} from 'recharts';

function App() {
  const [activeTab, setActiveTab] = useState('dashboard');
  const { 
    records, institutes, categories, loadInitialData, addRecord, 
    removeRecord, editRecord, saveInstitutes, saveCategories, isLoading 
  } = useStore();
  
  // Sorted Lists for UI
  const sortedInstitutes = useMemo(() => [...institutes].sort((a, b) => a.localeCompare(b)), [institutes]);
  const sortedCategories = useMemo(() => [...categories].sort((a, b) => a.localeCompare(b)), [categories]);

  // Form State
  const [formData, setFormData] = useState({
    institution: '',
    category: '',
    amount: '',
    entry_date: new Date().toISOString().split('T')[0]
  });

  // Modal State
  const [editingRecord, setEditingRecord] = useState(null);
  
  // Toast State
  const [toast, setToast] = useState(null);

  // Settings State
  const [newInstitute, setNewInstitute] = useState('');
  const [newCategory, setNewCategory] = useState('');

  // Forecast State
  const [forecastParams, setForecastParams] = useState({
    monthlySavings: 4000,
    yearlyGrowth: 5
  });

  useEffect(() => {
    loadInitialData();
  }, [loadInitialData]);

  // Set initial institution and category from lists if available
  useEffect(() => {
    if (sortedInstitutes.length > 0 && !formData.institution) {
      setFormData(prev => ({ ...prev, institution: sortedInstitutes[0] }));
    }
    if (sortedCategories.length > 0 && !formData.category) {
      setFormData(prev => ({ ...prev, category: sortedCategories[0] }));
    }
  }, [sortedInstitutes, sortedCategories]);

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 3000);
  };

  const handleSubmit = async (e, person) => {
    e.preventDefault();
    try {
      await addRecord({
        ...formData,
        amount: parseFloat(formData.amount),
        person
      });
      setFormData({
        institution: sortedInstitutes[0] || '',
        category: sortedCategories[0] || '',
        amount: '',
        entry_date: new Date().toISOString().split('T')[0]
      });
      showToast('Eintrag erfolgreich hinzugefügt');
    } catch (err) {
      showToast('Fehler beim Speichern', 'error');
    }
  };

  const handleEditSubmit = async (e) => {
    e.preventDefault();
    try {
      await editRecord(editingRecord.id, {
        ...editingRecord,
        amount: parseFloat(editingRecord.amount)
      });
      setEditingRecord(null);
      showToast('Eintrag erfolgreich aktualisiert');
    } catch (err) {
      showToast('Fehler beim Aktualisieren', 'error');
    }
  };

  const handleExport = () => {
    const dataStr = JSON.stringify({ records, institutes, categories }, null, 2);
    const dataUri = 'data:application/json;charset=utf-8,'+ encodeURIComponent(dataStr);
    const exportFileDefaultName = `finance_data_${new Date().toISOString().split('T')[0]}.json`;
    const linkElement = document.createElement('a');
    linkElement.setAttribute('href', dataUri);
    linkElement.setAttribute('download', exportFileDefaultName);
    linkElement.click();
    showToast('Daten exportiert');
  };

  const handleAddInstitute = async (e) => {
    e.preventDefault();
    if (newInstitute && !institutes.includes(newInstitute)) {
      const updatedList = [...institutes, newInstitute];
      await saveInstitutes(updatedList);
      setNewInstitute('');
      showToast('Institut hinzugefügt');
    }
  };

  const handleRemoveInstitute = async (inst) => {
    const updatedList = institutes.filter(i => i !== inst);
    await saveInstitutes(updatedList);
    showToast('Institut entfernt');
  };

  const handleAddCategory = async (e) => {
    e.preventDefault();
    if (newCategory && !categories.includes(newCategory)) {
      const updatedList = [...categories, newCategory];
      await saveCategories(updatedList);
      setNewCategory('');
      showToast('Kategorie hinzugefügt');
    }
  };

  const handleRemoveCategory = async (cat) => {
    const updatedList = categories.filter(c => c !== cat);
    await saveCategories(updatedList);
    showToast('Kategorie entfernt');
  };

  const COLORS = ['#6366f1', '#10b981', '#f59e0b', '#ef4444', '#8b5cf6', '#ec4899', '#06b6d4'];

  const calculateTotal = (person) => {
    const latestByInstitute = {};
    records.filter(r => !person || r.person === person).forEach(r => {
      const key = `${r.institution}-${r.category}`;
      if (!latestByInstitute[key] || new Date(r.entry_date) > new Date(latestByInstitute[key].entry_date)) {
        latestByInstitute[key] = r;
      }
    });
    return Object.values(latestByInstitute).reduce((sum, r) => sum + r.amount, 0);
  };

  const distributionData = useMemo(() => {
    const totals = {};
    const latestByInstitute = {};
    records.forEach(r => {
      const key = `${r.institution}-${r.category}`;
      if (!latestByInstitute[key] || new Date(r.entry_date) > new Date(latestByInstitute[key].entry_date)) {
        latestByInstitute[key] = r;
      }
    });
    Object.values(latestByInstitute).forEach(r => {
      totals[r.category] = (totals[r.category] || 0) + r.amount;
    });
    return Object.entries(totals).map(([name, value]) => ({ name, value }));
  }, [records]);

  const historyData = useMemo(() => {
    const sortedDates = [...new Set(records.map(r => r.entry_date))].sort();
    return sortedDates.map(date => {
      const dayTotal = records.filter(r => r.entry_date === date).reduce((sum, r) => sum + r.amount, 0);
      return { date, amount: dayTotal };
    });
  }, [records]);

  const generateForecast = () => {
    const years = 25;
    const currentTotal = calculateTotal();
    const data = [];
    let runningTotal = currentTotal;
    const currentYear = new Date().getFullYear();
    for (let i = 0; i <= years; i++) {
      const year = currentYear + i;
      data.push({
        year,
        amount: Math.round(runningTotal),
        ageJ: year - 1982,
        ageA: year - 1984
      });
      runningTotal = (runningTotal + forecastParams.monthlySavings * 12) * (1 + forecastParams.yearlyGrowth / 100);
    }
    return data;
  };

  const forecastData = generateForecast();

  const renderPersonTab = (person) => {
    const personRecords = records.filter(r => r.person === person)
      .sort((a, b) => new Date(b.entry_date) - new Date(a.entry_date));

    return (
      <div className="tab-content">
        <div className="card">
          <h3>Neuen Eintrag für {person}</h3>
          <form onSubmit={(e) => handleSubmit(e, person)} className="finance-form">
            <div className="form-group">
              <label>Institut / Bank</label>
              <select 
                value={formData.institution}
                onChange={(e) => setFormData({...formData, institution: e.target.value})}
                required
              >
                {sortedInstitutes.length === 0 && <option value="">Bitte Institute anlegen</option>}
                {sortedInstitutes.map(inst => <option key={inst} value={inst}>{inst}</option>)}
              </select>
            </div>
            <div className="form-group">
              <label>Kategorie</label>
              <select 
                value={formData.category}
                onChange={(e) => setFormData({...formData, category: e.target.value})}
                required
              >
                {sortedCategories.length === 0 && <option value="">Bitte Kategorien anlegen</option>}
                {sortedCategories.map(c => <option key={c} value={c}>{c}</option>)}
              </select>
            </div>
            <div className="form-group">
              <label>Betrag (€)</label>
              <input 
                type="number" 
                step="0.01"
                value={formData.amount}
                onChange={(e) => setFormData({...formData, amount: e.target.value})}
                placeholder="0.00"
                required
              />
            </div>
            <div className="form-group">
              <label>Datum</label>
              <input 
                type="date" 
                value={formData.entry_date}
                onChange={(e) => setFormData({...formData, entry_date: e.target.value})}
                required
              />
            </div>
            <button type="submit" className="btn-primary">
              <Plus size={18} /> Hinzufügen
            </button>
          </form>
        </div>

        <div className="card">
          <div className="card-header">
            <h3>Historie</h3>
          </div>
          {personRecords.length === 0 ? (
            <p className="text-muted">Noch keine Einträge vorhanden.</p>
          ) : (
            <div className="table-container">
              <table className="finance-table">
                <thead>
                  <tr>
                    <th>Datum</th>
                    <th>Institut</th>
                    <th>Kategorie</th>
                    <th className="text-right">Betrag</th>
                    <th>Aktionen</th>
                  </tr>
                </thead>
                <tbody>
                  {personRecords.map(r => (
                    <tr key={r.id}>
                      <td>{r.entry_date}</td>
                      <td>{r.institution}</td>
                      <td><span className="badge">{r.category}</span></td>
                      <td className="text-right bold">{r.amount.toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })}</td>
                      <td className="actions">
                        <button className="btn-icon" onClick={() => setEditingRecord(r)} title="Bearbeiten"><Edit2 size={16} /></button>
                        <button 
                          className="btn-icon delete" 
                          onClick={() => { if(confirm('Löschen?')) removeRecord(r.id); showToast('Eintrag gelöscht'); }}
                          title="Löschen"
                        >
                          <Trash2 size={16} />
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    );
  };

  return (
    <div className="container">
      {/* Toast Notification */}
      {toast && (
        <div className={`toast ${toast.type}`}>
          {toast.type === 'success' ? <CheckCircle size={18} /> : <AlertCircle size={18} />}
          {toast.message}
        </div>
      )}

      {/* Edit Modal */}
      {editingRecord && (
        <div className="modal-overlay">
          <div className="modal card">
            <div className="modal-header">
              <h3>Eintrag bearbeiten</h3>
              <button className="btn-icon" onClick={() => setEditingRecord(null)}><X size={20} /></button>
            </div>
            <form onSubmit={handleEditSubmit} className="modal-form">
              <div className="form-group">
                <label>Institut</label>
                <select 
                  value={editingRecord.institution}
                  onChange={(e) => setEditingRecord({...editingRecord, institution: e.target.value})}
                  required
                >
                  {sortedInstitutes.map(inst => <option key={inst} value={inst}>{inst}</option>)}
                </select>
              </div>
              <div className="form-group">
                <label>Kategorie</label>
                <select 
                  value={editingRecord.category}
                  onChange={(e) => setEditingRecord({...editingRecord, category: e.target.value})}
                  required
                >
                  {sortedCategories.map(c => <option key={c} value={c}>{c}</option>)}
                </select>
              </div>
              <div className="form-group">
                <label>Betrag (€)</label>
                <input 
                  type="number" 
                  step="0.01"
                  value={editingRecord.amount}
                  onChange={(e) => setEditingRecord({...editingRecord, amount: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Datum</label>
                <input 
                  type="date" 
                  value={editingRecord.entry_date}
                  onChange={(e) => setEditingRecord({...editingRecord, entry_date: e.target.value})}
                  required
                />
              </div>
              <div className="modal-actions">
                <button type="button" className="btn-secondary" onClick={() => setEditingRecord(null)}>Abbrechen</button>
                <button type="submit" className="btn-primary">Speichern</button>
              </div>
            </form>
          </div>
        </div>
      )}

      <header>
        <div className="logo-section">
          <div className="logo">
            <TrendingUp className="primary-color" />
            <h1>FinanceFlow</h1>
          </div>
          <div className="header-actions">
            <button className="btn-icon" onClick={handleExport} title="Daten exportieren"><Download size={20} /></button>
          </div>
        </div>
        <p className="subtitle">Gemeinsames Vermögenstracking für Jens & Annika</p>
      </header>
      
      <nav className="tabs">
        <button 
          className={`tab-btn ${activeTab === 'dashboard' ? 'active' : ''}`}
          onClick={() => setActiveTab('dashboard')}
        >
          <PieChartIcon size={18} /> Dashboard
        </button>
        <button 
          className={`tab-btn ${activeTab === 'jens' ? 'active' : ''}`}
          onClick={() => setActiveTab('jens')}
        >
          <Wallet size={18} /> Jens
        </button>
        <button 
          className={`tab-btn ${activeTab === 'annika' ? 'active' : ''}`}
          onClick={() => setActiveTab('annika')}
        >
          <Wallet size={18} /> Annika
        </button>
        <button 
          className={`tab-btn ${activeTab === 'settings' ? 'active' : ''}`}
          onClick={() => setActiveTab('settings')}
        >
          <SettingsIcon size={18} /> Einstellungen
        </button>
      </nav>

      <main>
        {activeTab === 'dashboard' && (
          <div className="dashboard">
            <div className="stats-grid">
              <div className="card stat-card">
                <p className="stat-label">Gesamtvermögen</p>
                <p className="stat-value">{calculateTotal().toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })}</p>
              </div>
              <div className="card stat-card">
                <p className="stat-label">Jens</p>
                <p className="stat-value secondary">{calculateTotal('Jens').toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })}</p>
              </div>
              <div className="card stat-card">
                <p className="stat-label">Annika</p>
                <p className="stat-value secondary">{calculateTotal('Annika').toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })}</p>
              </div>
            </div>
            
            <div className="charts-grid">
              <div className="card chart-card">
                <h3>Verteilung</h3>
                <div style={{ height: 300 }}>
                  <ResponsiveContainer width="100%" height="100%">
                    <PieChart>
                      <Pie
                        data={distributionData}
                        innerRadius={60}
                        outerRadius={80}
                        paddingAngle={5}
                        dataKey="value"
                        label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                      >
                        {distributionData.map((entry, index) => (
                          <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                        ))}
                      </Pie>
                      <RechartsTooltip formatter={(val) => val.toLocaleString('de-DE') + ' €'} />
                      <Legend />
                    </PieChart>
                  </ResponsiveContainer>
                </div>
              </div>

              <div className="card chart-card">
                <h3>Historie (Buchungen)</h3>
                <div style={{ height: 300 }}>
                  <ResponsiveContainer width="100%" height="100%">
                    <LineChart data={historyData}>
                      <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#334155" />
                      <XAxis dataKey="date" fontSize={10} tickMargin={10} />
                      <YAxis fontSize={10} tickFormatter={(val) => `${val/1000}k`} />
                      <RechartsTooltip formatter={(val) => val.toLocaleString('de-DE') + ' €'} />
                      <Line type="monotone" dataKey="amount" stroke="#6366f1" strokeWidth={3} dot={{ r: 4, fill: '#6366f1' }} />
                    </LineChart>
                  </ResponsiveContainer>
                </div>
              </div>
            </div>

            <div className="card">
              <h3>Zukunfts-Prognose (25 Jahre)</h3>
              <div style={{ height: 350, marginTop: '1.5rem' }}>
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={forecastData} margin={{ left: 40, right: 20 }}>
                    <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#334155" />
                    <XAxis 
                      dataKey="year" 
                      fontSize={10} 
                      tickMargin={10} 
                      height={60}
                      tick={({ x, y, payload, index }) => {
                        const d = forecastData.find(item => item.year === payload.value);
                        const isFirst = payload.value === forecastData[0].year;
                        return (
                          <g transform={`translate(${x},${y})`}>
                            <text x={0} y={0} dy={16} textAnchor="middle" fill="#94a3b8" fontSize={10}>{payload.value}</text>
                            {isFirst && <text x={-25} y={0} dy={30} textAnchor="end" fill="#6366f1" fontSize={9} fontWeight="bold">Jens</text>}
                            <text x={0} y={0} dy={30} textAnchor="middle" fill="#6366f1" fontSize={9}>{d?.ageJ}</text>
                            {isFirst && <text x={-25} y={0} dy={42} textAnchor="end" fill="#10b981" fontSize={9} fontWeight="bold">Annika</text>}
                            <text x={0} y={0} dy={42} textAnchor="middle" fill="#10b981" fontSize={9}>{d?.ageA}</text>
                          </g>
                        );
                      }}
                    />
                    <YAxis 
                      fontSize={10} 
                      tickFormatter={(val) => val >= 1000000 ? `${(val/1000000).toFixed(1)}M` : `${val/1000}k`} 
                    />
                    <RechartsTooltip 
                      formatter={(val) => val.toLocaleString('de-DE') + ' €'}
                      contentStyle={{ backgroundColor: '#1e293b', border: '1px solid #334155', borderRadius: '8px' }}
                      itemStyle={{ color: '#fff' }}
                    />
                    <ReferenceLine 
                      y={2000000} 
                      label={{ value: 'Sparziel: 2 Mio. €', position: 'top', fill: '#10b981', fontSize: 12, fontWeight: 'bold' }} 
                      stroke="#10b981" 
                      strokeDasharray="5 5" 
                      strokeWidth={2}
                    />
                    <Bar dataKey="amount" fill="url(#barGradient)" radius={[4, 4, 0, 0]} />
                    <defs>
                      <linearGradient id="barGradient" x1="0" y1="0" x2="0" y2="1">
                        <stop offset="0%" stopColor="#818cf8" stopOpacity={1} />
                        <stop offset="100%" stopColor="#6366f1" stopOpacity={0.8} />
                      </linearGradient>
                    </defs>
                  </BarChart>
                </ResponsiveContainer>
              </div>
              <div className="prognose-params">
                <div className="form-group">
                  <label>Sparrate (€/Monat)</label>
                  <input 
                    type="number" 
                    value={forecastParams.monthlySavings}
                    onChange={(e) => setForecastParams({...forecastParams, monthlySavings: parseFloat(e.target.value) || 0})}
                  />
                </div>
                <div className="form-group">
                  <label>Wachstum (% p.a.)</label>
                  <input 
                    type="number" 
                    value={forecastParams.yearlyGrowth}
                    onChange={(e) => setForecastParams({...forecastParams, yearlyGrowth: parseFloat(e.target.value) || 0})}
                  />
                </div>
              </div>
            </div>
          </div>
        )}
        
        {activeTab === 'jens' && renderPersonTab('Jens')}
        {activeTab === 'annika' && renderPersonTab('Annika')}

        {activeTab === 'settings' && (
          <div className="settings">
            <div className="card">
              <h3>Finanz-Einstellungen</h3>
              
              <div className="settings-section">
                <h4>Institute verwalten</h4>
                <p className="text-muted">Banken und Broker hinzufügen oder entfernen.</p>
                <form onSubmit={handleAddInstitute} className="finance-form" style={{ marginBottom: '1.5rem' }}>
                  <div className="form-group" style={{ flex: 1 }}>
                    <input 
                      type="text" 
                      value={newInstitute}
                      onChange={(e) => setNewInstitute(e.target.value)}
                      placeholder="z.B. Deutsche Bank"
                    />
                  </div>
                  <button type="submit" className="btn-primary">
                    <Plus size={18} /> Hinzufügen
                  </button>
                </form>
                <div className="institute-list">
                  {sortedInstitutes.map(inst => (
                    <div key={inst} className="institute-item card">
                      <span>{inst}</span>
                      <button className="btn-icon delete" onClick={() => handleRemoveInstitute(inst)}>
                        <Trash2 size={18} />
                      </button>
                    </div>
                  ))}
                </div>
              </div>

              <hr className="divider" />

              <div className="settings-section">
                <h4>Kategorien verwalten</h4>
                <p className="text-muted">Finanzkategorien für deine Buchungen anpassen.</p>
                <form onSubmit={handleAddCategory} className="finance-form" style={{ marginBottom: '1.5rem' }}>
                  <div className="form-group" style={{ flex: 1 }}>
                    <input 
                      type="text" 
                      value={newCategory}
                      onChange={(e) => setNewCategory(e.target.value)}
                      placeholder="z.B. Auto, Urlaub"
                    />
                  </div>
                  <button type="submit" className="btn-primary">
                    <Plus size={18} /> Hinzufügen
                  </button>
                </form>
                <div className="institute-list">
                  {sortedCategories.map(cat => (
                    <div key={cat} className="institute-item card">
                      <span>{cat}</span>
                      <button className="btn-icon delete" onClick={() => handleRemoveCategory(cat)}>
                        <Trash2 size={18} />
                      </button>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        )}
      </main>
    </div>
  );
}

export default App;
