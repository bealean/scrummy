import axios from 'axios';

export default {
    getMealById(mealId) {
        return axios.get(`/meals/${mealId}`);
    }
}