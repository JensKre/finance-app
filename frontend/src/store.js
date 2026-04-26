import { create } from 'zustand';
import * as api from './api';

const useStore = create((set, get) => ({
  records: [],
  institutes: [],
  categories: [],
  isLoading: false,
  error: null,

  loadInitialData: async () => {
    set({ isLoading: true });
    try {
      const [records, institutes, categories] = await Promise.all([
        api.fetchRecords(),
        api.fetchInstitutes(),
        api.fetchCategories()
      ]);
      set({ records, institutes, categories, isLoading: false });
    } catch (error) {
      set({ error: error.message, isLoading: false });
    }
  },

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
      set((state) => ({ records: [newRecord, ...state.records] }));
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

  editRecord: async (id, updatedRecord) => {
    try {
      const result = await api.updateRecord(id, updatedRecord);
      set((state) => ({
        records: state.records.map((r) => (r.id === id ? result : r)),
      }));
    } catch (error) {
      set({ error: error.message });
    }
  },

  saveInstitutes: async (institutes) => {
    try {
      const updatedList = await api.updateInstitutes(institutes);
      set({ institutes: updatedList });
    } catch (error) {
      set({ error: error.message });
    }
  },

  saveCategories: async (categories) => {
    try {
      const updatedList = await api.updateCategories(categories);
      set({ categories: updatedList });
    } catch (error) {
      set({ error: error.message });
    }
  },
}));

export default useStore;
