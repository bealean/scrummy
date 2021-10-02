import axios from 'axios';

export default {
    getAllMealPlans() {
        return axios.get('/meal-plans');
    },
    getMealPlanByName(mealPlanName) {
        return axios.get('/my-meal-plan', { params: { mealPlanName } });
    },
    getMealPlanById(mealPlanId) {
        return axios.get(`/meal-plans/${mealPlanId}`);
    },
    getIngredientsByMealPlanIds(id) {
        return axios.get(`/grocery-list?${id}`);
    },
    newMealPlan(mealPlanName) {
        return axios.post(`/add-new-meal-plan?mealPlanName=${mealPlanName}`);
    },
    updateMealPlan(mealPlanId, mealPlan) {
        return axios.put(`/update-meal-plan/${mealPlanId}`, mealPlan);
    },
    deleteMealPlan(mealPlanId) {
        return axios.delete(`/delete-meal-plan/${mealPlanId}`);
    }
}