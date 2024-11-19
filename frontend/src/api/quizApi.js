import api from '@/api/api.js'

export const getRandomQuiz = async () => {
  try {
    const response = await api.get('/quiz/random')
    return response.data
  } catch (error) {
    return error.data
  }
}

export const getQuizResult = async (quizId) => {
  try {
    const response = await api.get(`/quiz/${quizId}/result`)
    return response.data
  } catch (error) {
    return error.data
  }
}
