import React, { useState, useEffect, useMemo } from 'react';
import useStore from './store';
import { 
  Plus, Trash2, Edit2, TrendingUp, Wallet, PieChart as PieChartIcon, 
  Download, Upload, X, CheckCircle, AlertCircle 
} from 'lucide-react';
import { 
  PieChart, Pie, Cell, ResponsiveContainer, Tooltip as RechartsTooltip, 
  LineChart, Line, XAxis, YAxis, CartesianGrid, Legend 
} from 'recharts';

function App() {
  const [activeTab, setActiveTab] = useState('dashboard');
  const { records, loadRecords, addRecord, removeRecord, editRecord, isLoading } = useStore();
  
  // Form State
  const [formData, setFormData] = useState({
    institution: '',
    category: 'Girokonto',
    amount: '',
    entry_date: new Date().toISOString().split('T')[0]
  });

  // Modal State
  const [editingRecord, setEditingRecord] = useState(null);
  
  // Toast State
  const [toast, setToast] = useState(null);

  // Forecast State
  const [forecastParams, setForecastParams] = useState({
    monthlySavings: 500,
    yearlyGrowth: 5
  });

  useEffect(() => {
    loadRecords();
  }, [loadRecords]);

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
        institution: '',
        category: 'Girokonto',
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
    const dataStr = JSON.stringify({ records }, null, 2);
    const dataUri = 'data:application/json;charset=utf-8,'+ encodeURIComponent(dataStr);
    const exportFileDefaultName = `finance_data_${new Date().toISOString().split('T')[0]}.json`;
    const linkElement = document.createElement('a');
    linkElement.setAttribute('href', dataUri);
    linkElement.setAttribute('download', exportFileDefaultName);
    linkElement.click();
    showToast('Daten exportiert');
  };

  const categories = ['Girokonto', 'Tagesgeld', 'ETF', 'Gold', 'Krypto'];
  const COLORS = ['#6366f1', '#10b981', '#f59e0b', '#ef4444', '#8b5cf6'];

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
    // Group records by date and calculate running total
    const sortedDates = [...new Set(records.map(r => r.entry_date))].sort();
    let runningTotal = 0;
    // Note: Simple version - just sums everything on that day. 
    // In a real app, you'd need the balance at each point in time.
    return sortedDates.map(date => {
      const dayTotal = records.filter(r => r.entry_date === date).reduce((sum, r) => sum + r.amount, 0);
      return { date, amount: dayTotal };
    });
  }, [records]);

  const generateForecast = () => {
    const years = 10;
    const currentTotal = calculateTotal();
    const data = [];
    let runningTotal = currentTotal;
    for (let i = 0; i <= years; i++) {
      data.push({
        year: new Date().getFullYear() + i,
        amount: runningTotal
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
              <input 
                type="text" 
                value={formData.institution}
                onChange={(e) => setFormData({...formData, institution: e.target.value})}
                placeholder="z.B. Sparkasse, Trade Republic"
                required
              />
            </div>
            <div className="form-group">
              <label>Kategorie</label>
              <select 
                value={formData.category}
                onChange={(e) => setFormData({...formData, category: e.target.value})}
              >
                {categories.map(c => <option key={c} value={c}>{c}</option>)}
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
                <input 
                  type="text" 
                  value={editingRecord.institution}
                  onChange={(e) => setEditingRecord({...editingRecord, institution: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Kategorie</label>
                <select 
                  value={editingRecord.category}
                  onChange={(e) => setEditingRecord({...editingRecord, category: e.target.value})}
                >
                  {categories.map(c => <option key={c} value={c}>{c}</option>)}
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
              <h3>Zukunfts-Prognose (10 Jahre)</h3>
              <div className="forecast-chart">
                <div className="chart-bars">
                  {forecastData.map(d => (
                    <div key={d.year} className="chart-bar-container">
                      <div 
                        className="chart-bar" 
                        style={{ height: `${Math.min(100, (d.amount / forecastData[forecastData.length - 1].amount) * 100)}%` }}
                      >
                        <span className="bar-tooltip">{d.amount.toLocaleString('de-DE', { maximumFractionDigits: 0 })} €</span>
                      </div>
                      <span className="bar-year">{d.year}</span>
                    </div>
                  ))}
                </div>
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
      </main>
    </div>
  );
}

export default App;
