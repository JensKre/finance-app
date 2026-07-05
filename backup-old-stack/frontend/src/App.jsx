import React, { useState, useEffect, useMemo } from 'react';
import useStore from './store';
import { 
  Plus, Trash2, Edit2, TrendingUp, Wallet, PieChart as PieChartIcon, 
  Download, Upload, X, CheckCircle, AlertCircle, Settings as SettingsIcon,
  ChevronUp, ChevronDown, ArrowUpDown, Layout, BookOpen
} from 'lucide-react';
import { 
  PieChart, Pie, Cell, ResponsiveContainer, Tooltip as RechartsTooltip, 
  LineChart, Line, XAxis, YAxis, CartesianGrid, Legend, BarChart, Bar, 
  ReferenceLine, AreaChart, Area 
} from 'recharts';

function App() {
  const [activeModule, setActiveModule] = useState('wealth');
  const [activeTab, setActiveTab] = useState('dashboard');
  const [budgetTimeScale, setBudgetTimeScale] = useState('monthly'); // 'monthly' or 'yearly'
  const [showBudgetAverage, setShowBudgetAverage] = useState(false);
  const [budgetDateRange, setBudgetDateRange] = useState({ 
    startMonth: '', startYear: '', 
    endMonth: '', endYear: '' 
  });
  const { 
    records, budgetRecords, institutes, categories, loadInitialData, addRecord, 
    removeRecord, editRecord, saveInstitutes, saveCategories, isLoading,
    importBudget, clearBudget
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

  const [sortConfig, setSortConfig] = useState([{ key: 'entry_date', direction: 'desc' }]);

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

  const handleFileUpload = (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = async (event) => {
      const text = event.target.result;
      const lines = text.split('\n');
      const headers = lines[0].split(';');
      
      const newRecords = lines.slice(1)
        .filter(line => line.trim() && line.includes(';'))
        .map(line => {
          const parts = line.split(';');
          return {
            date: (parts[0] || '').trim(),
            type: (parts[1] || '').trim(),
            amount: parseFloat((parts[2] || '0').replace(',', '.')) || 0,
            category: (parts[3] || 'Sonstiges').trim(),
            person: (parts[4] || 'Unbekannt').trim(),
            description: (parts[5] || '').trim()
          };
        });

      if (newRecords.length === 0) {
        showToast('Keine gültigen Daten gefunden', 'error');
        e.target.value = '';
        return;
      }

      try {
        const success = await importBudget(newRecords);
        if (success) {
          showToast(`${newRecords.length} Einträge importiert`);
        } else {
          showToast('Fehler beim Speichern der Daten', 'error');
        }
      } catch (err) {
        showToast('Import fehlgeschlagen', 'error');
      } finally {
        e.target.value = '';
      }
    };
    reader.readAsText(file);
  };

  const filteredTrendRecords = useMemo(() => {
    const { startYear, startMonth, endYear, endMonth } = budgetDateRange;
    const startStr = startYear && startMonth ? `${startYear}-${startMonth}` : '';
    const endStr = endYear && endMonth ? `${endYear}-${endMonth}` : '';

    if (!startStr && !endStr) return budgetRecords;
    
    return budgetRecords.filter(r => {
      const parts = r.date.split('.');
      if (parts.length !== 3) return true;
      const recordMonth = `${parts[2]}-${parts[1]}`;
      
      if (startStr && recordMonth < startStr) return false;
      if (endStr && recordMonth > endStr) return false;
      return true;
    });
  }, [budgetRecords, budgetDateRange]);

  const budgetAnalysis = useMemo(() => {
    if (!budgetRecords.length) return null;

    const byType = { Einnahme: 0, Ausgabe: 0 };
    const byCategory = {};
    const byPerson = {};
    const overTime = {};

    // Stats use ALL records
    budgetRecords.forEach(r => {
      if (byType[r.type] !== undefined) byType[r.type] += r.amount;
      if (r.type === 'Ausgabe') {
        byCategory[r.category] = (byCategory[r.category] || 0) + r.amount;
      }
      if (!byPerson[r.person]) byPerson[r.person] = { Einnahme: 0, Ausgabe: 0 };
      byPerson[r.person][r.type] += r.amount;

      const dateParts = r.date.split('.');
      if (dateParts.length === 3) {
        const monthYear = `${dateParts[2]}-${dateParts[1]}`;
        if (!overTime[monthYear]) overTime[monthYear] = { month: monthYear, Einnahme: 0, Ausgabe: 0 };
        overTime[monthYear][r.type] += r.amount;
      }
    });

    const categoryData = Object.entries(byCategory)
      .map(([name, value]) => ({ name, value }))
      .sort((a, b) => b.value - a.value);

    const personData = Object.entries(byPerson).map(([name, values]) => ({
      name,
      ...values
    }));

    const timelineData = Object.values(overTime).sort((a, b) => a.month.localeCompare(b.month));

    // Category Trend Analysis (Using FILTERED records)
    const categoryTrendData = {};
    const allCategories = new Set();
    const categoryYearlyTrendData = {};
    
    filteredTrendRecords.forEach(r => {
      const dateParts = r.date.split('.');
      if (dateParts.length === 3) {
        const monthYear = `${dateParts[2]}-${dateParts[1]}`;
        const year = dateParts[2];

        if (!categoryTrendData[monthYear]) categoryTrendData[monthYear] = { month: monthYear };
        categoryTrendData[monthYear][r.category] = (categoryTrendData[monthYear][r.category] || 0) + r.amount;
        
        if (!categoryYearlyTrendData[year]) categoryYearlyTrendData[year] = { year };
        categoryYearlyTrendData[year][r.category] = (categoryYearlyTrendData[year][r.category] || 0) + r.amount;

        allCategories.add(r.category);
      }
    });

    const categoryTimeline = Object.values(categoryTrendData).sort((a, b) => a.month.localeCompare(b.month));
    const categoryYearlyTimeline = Object.values(categoryYearlyTrendData).sort((a, b) => a.year.localeCompare(b.year));
    const uniqueCategories = Array.from(allCategories);

    return {
      typeData: [
        { name: 'Einnahmen', value: byType.Einnahme },
        { name: 'Ausgaben', value: byType.Ausgabe }
      ],
      categoryData,
      personData,
      timelineData,
      categoryTimeline,
      categoryYearlyTimeline,
      uniqueCategories
    };
  }, [budgetRecords, filteredTrendRecords]);

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
      const key = `${r.person}-${r.institution}-${r.category}`;
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
      const key = `${r.person}-${r.institution}-${r.category}`;
      if (!latestByInstitute[key] || new Date(r.entry_date) > new Date(latestByInstitute[key].entry_date)) {
        latestByInstitute[key] = r;
      }
    });
    Object.values(latestByInstitute).forEach(r => {
      totals[r.category] = (totals[r.category] || 0) + r.amount;
    });
    // Filter out categories with 0 or negative total for the pie chart
    return Object.entries(totals)
      .filter(([_, value]) => value > 0)
      .map(([name, value]) => ({ name, value }));
  }, [records]);

  const historyData = useMemo(() => {
    const sortedDates = [...new Set(records.map(r => r.entry_date))].sort();
    return sortedDates.map(date => {
      const latestByInstitute = {};
      records.filter(r => new Date(r.entry_date) <= new Date(date)).forEach(r => {
        const key = `${r.person}-${r.institution}-${r.category}`;
        if (!latestByInstitute[key] || new Date(r.entry_date) > new Date(latestByInstitute[key].entry_date)) {
          latestByInstitute[key] = r;
        }
      });
      const dayTotal = Object.values(latestByInstitute).reduce((sum, r) => sum + r.amount, 0);
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
      runningTotal = (runningTotal + forecastParams.monthlySavings * 12) * (1 + (parseFloat(String(forecastParams.yearlyGrowth).replace(',', '.')) || 0) / 100);
    }
    return data;
  };

  const forecastData = generateForecast();

  const renderPersonTab = (person) => {
    const handleSort = (key) => {
      setSortConfig(prev => {
        const existing = prev.find(s => s.key === key);
        const others = prev.filter(s => s.key !== key);
        
        if (existing) {
          // If already primary, toggle direction
          if (prev[0].key === key) {
            return [{ key, direction: existing.direction === 'asc' ? 'desc' : 'asc' }, ...others];
          }
          // Move to primary
          return [existing, ...others];
        }
        // Add new as primary
        return [{ key, direction: 'asc' }, ...prev];
      });
    };

    const getSortIcon = (key) => {
      const index = sortConfig.findIndex(s => s.key === key);
      if (index === -1) return <ArrowUpDown size={14} className="sort-icon-muted" />;
      const icon = sortConfig[index].direction === 'asc' ? <ChevronUp size={14} /> : <ChevronDown size={14} />;
      return (
        <span className="sort-indicator">
          {icon}
          {sortConfig.length > 1 && index < 3 && <span className="sort-priority">{index + 1}</span>}
        </span>
      );
    };

    const personRecords = records.filter(r => r.person === person)
      .sort((a, b) => {
        for (const { key, direction } of sortConfig) {
          let comparison = 0;
          if (key === 'entry_date') {
            comparison = new Date(a.entry_date) - new Date(b.entry_date);
          } else if (key === 'amount') {
            comparison = a.amount - b.amount;
          } else {
            const valA = a[key]?.toLowerCase() || '';
            const valB = b[key]?.toLowerCase() || '';
            if (valA < valB) comparison = -1;
            else if (valA > valB) comparison = 1;
          }
          
          if (comparison !== 0) {
            return direction === 'asc' ? comparison : -comparison;
          }
        }
        return 0;
      });

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
                    <th onClick={() => handleSort('entry_date')} className="sortable">
                      Datum {getSortIcon('entry_date')}
                    </th>
                    <th onClick={() => handleSort('institution')} className="sortable">
                      Institut {getSortIcon('institution')}
                    </th>
                    <th onClick={() => handleSort('category')} className="sortable">
                      Kategorie {getSortIcon('category')}
                    </th>
                    <th onClick={() => handleSort('amount')} className="text-right sortable">
                      Betrag {getSortIcon('amount')}
                    </th>
                    <th>Aktionen</th>
                  </tr>
                </thead>
                <tbody>
                  {personRecords.map(r => (
                    <tr key={r.id}>
                      <td>{r.entry_date}</td>
                      <td>{r.institution}</td>
                      <td><span className="badge">{r.category}</span></td>
                      <td className={`text-right bold ${r.amount < 0 ? 'text-danger' : ''}`}>
                        {r.amount.toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })}
                      </td>
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
    <div className="app-container">
      <aside className="sidebar">
        <div className="sidebar-logo">
          <TrendingUp size={24} className="primary-color" />
        </div>
        <nav className="sidebar-nav">
          <button 
            className={`sidebar-btn ${activeModule === 'wealth' ? 'active' : ''}`} 
            onClick={() => setActiveModule('wealth')}
            title="Vermögensübersicht"
          >
            <Layout size={20} />
            <span>Vermögen</span>
          </button>
          <button 
            className={`sidebar-btn ${activeModule === 'budget' ? 'active' : ''}`} 
            onClick={() => setActiveModule('budget')}
            title="Haushaltsbuch"
          >
            <BookOpen size={20} />
            <span>Haushalt</span>
          </button>
        </nav>
      </aside>

      <div className="main-content-wrapper">
        {activeModule === 'wealth' ? (
          <>
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
                  <h1>FinanceFlow <span className="module-tag">Vermögen</span></h1>
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
                              label={({ name, percent }) => {
                                const p = percent * 100;
                                const formattedPercent = p < 1 ? p.toFixed(1).replace('.', ',') : p.toFixed(0);
                                return `${name} ${formattedPercent}%`;
                              }}
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
                          type="text" 
                          value={forecastParams.yearlyGrowth}
                          onChange={(e) => {
                            const val = e.target.value.replace(/[^0-9,.]/g, '');
                            setForecastParams({...forecastParams, yearlyGrowth: val});
                          }}
                          placeholder="0,0"
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
                            placeholder="Neues Institut"
                          />
                        </div>
                        <button type="submit" className="btn-primary">Hinzufügen</button>
                      </form>
                      <div className="settings-list">
                        {sortedInstitutes.map(inst => (
                          <div key={inst} className="settings-item">
                            <span>{inst}</span>
                            <button className="btn-icon delete" onClick={() => removeInstitute(inst)}><Trash2 size={16} /></button>
                          </div>
                        ))}
                      </div>
                    </div>
                    <div className="settings-section">
                      <h4>Kategorien verwalten</h4>
                      <p className="text-muted">Finanzkategorien anpassen.</p>
                      <form onSubmit={handleAddCategory} className="finance-form" style={{ marginBottom: '1.5rem' }}>
                        <div className="form-group" style={{ flex: 1 }}>
                          <input 
                            type="text" 
                            value={newCategory}
                            onChange={(e) => setNewCategory(e.target.value)}
                            placeholder="Neue Kategorie"
                          />
                        </div>
                        <button type="submit" className="btn-primary">Hinzufügen</button>
                      </form>
                      <div className="settings-list">
                        {sortedCategories.map(cat => (
                          <div key={cat} className="settings-item">
                            <span>{cat}</span>
                            <button className="btn-icon delete" onClick={() => removeCategory(cat)}><Trash2 size={16} /></button>
                          </div>
                        ))}
                      </div>
                    </div>
                  </div>
                </div>
              )}
            </main>
          </>
        ) : (
          <div className="budget-module">
            <header>
              <div className="logo-section">
                <div className="logo">
                  <BookOpen className="primary-color" />
                  <h1>FinanceFlow <span className="module-tag">Haushaltsbuch</span></h1>
                </div>
                <div className="header-actions">
                  <label className="btn-primary" style={{ cursor: 'pointer' }}>
                    <Upload size={18} /> CSV Import
                    <input type="file" accept=".csv" onChange={handleFileUpload} style={{ display: 'none' }} />
                  </label>
                  <button className="btn-icon delete" onClick={() => { if(confirm('Alle Daten löschen?')) clearBudget(); }} title="Daten leeren">
                    <Trash2 size={20} />
                  </button>
                </div>
              </div>
              <p className="subtitle">Monatliche Einnahmen & Ausgaben</p>
            </header>

            <div className="stats-grid">
              <div className="card stat-card">
                <p className="stat-label">Gesamt Ausgaben</p>
                <p className="stat-value text-danger">
                  {budgetRecords
                    .filter(r => r.type === 'Ausgabe')
                    .reduce((sum, r) => sum + r.amount, 0)
                    .toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })}
                </p>
              </div>
              <div className="card stat-card">
                <p className="stat-label">Gesamt Einnahmen</p>
                <p className="stat-value secondary">
                  {budgetRecords
                    .filter(r => r.type === 'Einnahme')
                    .reduce((sum, r) => sum + r.amount, 0)
                    .toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })}
                </p>
              </div>
              <div className="card stat-card">
                <p className="stat-label">Bilanz</p>
                <p className="stat-value">
                  {(budgetRecords.reduce((sum, r) => sum + (r.type === 'Einnahme' ? r.amount : -r.amount), 0))
                    .toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })}
                </p>
              </div>
            </div>

            {budgetAnalysis && (
              <div className="charts-grid" style={{ marginTop: '2rem' }}>

                <div className="card chart-card" style={{ gridColumn: 'span 2' }}>
                  <div className="header-actions" style={{ marginBottom: '1.5rem', flexWrap: 'wrap', gap: '1rem' }}>
                    <h3 style={{ margin: 0 }}>Kategorie-Trends (Einzeln)</h3>
                    
                    <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginLeft: 'auto' }}>
                      {/* Date Range Group */}
                      <div className="date-range-controls" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', background: 'var(--bg)', padding: '4px 12px', borderRadius: '0.5rem', border: '1px solid var(--border)' }}>
                        <span style={{ fontSize: '0.7rem', color: 'var(--text-muted)', fontWeight: 600 }}>VON</span>
                        <div style={{ display: 'flex', gap: '2px' }}>
                          <select 
                            className="select-minimal"
                            value={budgetDateRange.startMonth}
                            onChange={(e) => setBudgetDateRange({...budgetDateRange, startMonth: e.target.value})}
                          >
                            <option value="">Monat</option>
                            {['01','02','03','04','05','06','07','08','09','10','11','12'].map(m => <option key={m} value={m}>{m}</option>)}
                          </select>
                          <select 
                            className="select-minimal"
                            value={budgetDateRange.startYear}
                            onChange={(e) => setBudgetDateRange({...budgetDateRange, startYear: e.target.value})}
                          >
                            <option value="">Jahr</option>
                            {[2024, 2025, 2026, 2027].map(y => <option key={y} value={y}>{y}</option>)}
                          </select>
                        </div>

                        <span style={{ fontSize: '0.7rem', color: 'var(--text-muted)', fontWeight: 600, marginLeft: '0.5rem' }}>BIS</span>
                        <div style={{ display: 'flex', gap: '2px' }}>
                          <select 
                            className="select-minimal"
                            value={budgetDateRange.endMonth}
                            onChange={(e) => setBudgetDateRange({...budgetDateRange, endMonth: e.target.value})}
                          >
                            <option value="">Monat</option>
                            {['01','02','03','04','05','06','07','08','09','10','11','12'].map(m => <option key={m} value={m}>{m}</option>)}
                          </select>
                          <select 
                            className="select-minimal"
                            value={budgetDateRange.endYear}
                            onChange={(e) => setBudgetDateRange({...budgetDateRange, endYear: e.target.value})}
                          >
                            <option value="">Jahr</option>
                            {[2024, 2025, 2026, 2027].map(y => <option key={y} value={y}>{y}</option>)}
                          </select>
                        </div>

                        {(budgetDateRange.startMonth || budgetDateRange.startYear || budgetDateRange.endMonth || budgetDateRange.endYear) && (
                          <button 
                            className="btn-icon delete" 
                            style={{ padding: '2px', marginLeft: '0.5rem' }}
                            title="Filter zurücksetzen"
                            onClick={() => setBudgetDateRange({ startMonth: '', startYear: '', endMonth: '', endYear: '' })}
                          >
                            <X size={14} />
                          </button>
                        )}
                      </div>

                      {/* View Toggles */}
                      <div className="toggle-group">
                        <div className="segmented-control">
                          <button 
                            className={budgetTimeScale === 'monthly' ? 'active' : ''} 
                            onClick={() => setBudgetTimeScale('monthly')}
                          >Monat</button>
                          <button 
                            className={budgetTimeScale === 'yearly' ? 'active' : ''} 
                            onClick={() => setBudgetTimeScale('yearly')}
                          >Jahr</button>
                        </div>
                        <button 
                          className={`btn-secondary ${showBudgetAverage ? 'active' : ''}`}
                          onClick={() => setShowBudgetAverage(!showBudgetAverage)}
                          style={{ fontSize: '0.75rem', padding: '0.4rem 0.8rem' }}
                        >
                          Ø Mittelwert
                        </button>
                      </div>
                    </div>
                  </div>

                  <div className="mini-charts-grid">
                    {budgetAnalysis.uniqueCategories.map((cat, idx) => {
                      const chartData = budgetTimeScale === 'monthly' ? budgetAnalysis.categoryTimeline : budgetAnalysis.categoryYearlyTimeline;
                      const xKey = budgetTimeScale === 'monthly' ? 'month' : 'year';
                      
                      // Calculate average for the category
                      const values = chartData.map(d => d[cat] || 0);
                      const avg = values.length > 0 ? values.reduce((a, b) => a + b, 0) / values.length : 0;

                      return (
                        <div key={cat} className="card mini-chart-card">
                          <h4>{cat}</h4>
                          <div style={{ height: 150 }}>
                            <ResponsiveContainer width="100%" height="100%">
                              <AreaChart data={chartData}>
                                <defs>
                                  <linearGradient id={`color-${idx}`} x1="0" y1="0" x2="0" y2="1">
                                    <stop offset="5%" stopColor={COLORS[idx % COLORS.length]} stopOpacity={0.3}/>
                                    <stop offset="95%" stopColor={COLORS[idx % COLORS.length]} stopOpacity={0}/>
                                  </linearGradient>
                                </defs>
                                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#334155" />
                                <XAxis dataKey={xKey} fontSize={8} tickMargin={5} />
                                <YAxis fontSize={8} />
                                <RechartsTooltip 
                                  formatter={(val) => val.toLocaleString('de-DE') + ' €'}
                                  contentStyle={{ backgroundColor: '#1e293b', border: 'none', borderRadius: '8px', fontSize: '10px' }}
                                />
                                {showBudgetAverage && (
                                  <ReferenceLine 
                                    y={avg} 
                                    stroke="#94a3b8" 
                                    strokeDasharray="3 3" 
                                    label={{ 
                                      value: `Ø ${avg.toFixed(0)}€`, 
                                      position: 'insideTopRight', 
                                      fill: '#94a3b8', 
                                      fontSize: 10,
                                      fontWeight: 'bold',
                                      dy: -10
                                    }} 
                                  />
                                )}
                                <Area 
                                  type="monotone" 
                                  dataKey={cat} 
                                  stroke={COLORS[idx % COLORS.length]} 
                                  fillOpacity={1} 
                                  fill={`url(#color-${idx})`} 
                                  strokeWidth={2}
                                  dot={{ r: 3, fill: COLORS[idx % COLORS.length], strokeWidth: 1 }}
                                  activeDot={{ r: 5 }}
                                  connectNulls
                                />
                              </AreaChart>
                            </ResponsiveContainer>
                          </div>
                        </div>
                      );
                    })}
                  </div>
                </div>

                <div className="card chart-card" style={{ gridColumn: 'span 2' }}>
                  <h3>Einnahmen vs. Ausgaben (Trend)</h3>
                  <div style={{ height: 300 }}>
                    <ResponsiveContainer width="100%" height="100%">
                      <LineChart data={budgetAnalysis.timelineData}>
                        <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#334155" />
                        <XAxis dataKey="month" fontSize={10} />
                        <YAxis fontSize={10} />
                        <RechartsTooltip formatter={(val) => val.toLocaleString('de-DE') + ' €'} />
                        <Legend />
                        <Line type="monotone" dataKey="Einnahme" stroke="var(--secondary)" strokeWidth={3} name="Einnahmen" dot={{ r: 4, fill: "var(--secondary)" }} activeDot={{ r: 6 }} />
                        <Line type="monotone" dataKey="Ausgabe" stroke="#ef4444" strokeWidth={3} name="Ausgaben" dot={{ r: 4, fill: "#ef4444" }} activeDot={{ r: 6 }} />
                      </LineChart>
                    </ResponsiveContainer>
                  </div>
                </div>
              </div>
            )}

          </div>
        )}

        {/* Global Toast */}
        {toast && (
          <div className={`toast ${toast.type}`}>
            {toast.type === 'success' ? <CheckCircle size={18} /> : <AlertCircle size={18} />}
            {toast.message}
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
