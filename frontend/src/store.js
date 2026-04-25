import { create } from 'zustand';
import * as api from './api';

const useStore = create((set, get) => ({
  records: [],
  isLoading: false,
  error: null,

  loadRecords: async () => {
    set({ isLoading: true });
    try {
      const records = await api.fetchRecords();
      set({ records, isLoading: false });
    } catch (error) {
      set({ error: error.message, isLoading: false });
    }
  },

  addRecord: async (record) => {
    try {
      const newRecord = await api.createRecord(record);
      set((state) => ({ records: [...state.records, newRecord] }));
    } catch (error) {
      set({ error: error.message });
    }
  },

  removeRecord: async (id) => {
    try {
      await api.deleteRecord(id);
      set((state) => ({ records: state.records.filter((r) => r.id !== id) }));
    } catch (error) {
      set({ error: error.message });
    }
  },

  editRecord: async (id, record) => {
    try {
      const updatedRecord = await api.updateRecord(id, record);
      set((state) => ({
        records: state.records.map((r) => (r.id === id ? updatedRecord : r)),
      }));
    } catch (error) {
      set({ error: error.message });
    }
  },
}));

export default useStore;
