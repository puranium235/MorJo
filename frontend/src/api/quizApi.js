import api from '@/api/api.js'

export const getRandomQuiz = async () => {
  try {
    const response = await api.get('/quiz/random')
    return response.data
  } catch (error) {
    return error.data
  }
}

export const getQuiz = async (quizId) => {
  try {
    const response = await api.get(`/quiz/${quizId}`)
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

export const postQuizCreate = async ({ content, option1, option2, option3, option4, answer }) => {
  try {
    const response = await api.post('/quiz/create', {
      content,
      option1,
      option2,
      option3,
      option4,
      answer,
    })
    return response.data
  } catch (error) {
    throw error
  }
}

export const getSpecificQuiz = async (quizId) => {
  try {
    const response = await api.get(`/quiz/${quizId}`)
    return response.data
  } catch (error) {
    return error.data
  }
}

export const postQuizSubmit = async ({ quizId, userAnswer, isCommonSense }) => {
  try {
    const response = await api.post('/quiz/submit', {
      quizId,
      userAnswer,
      isCommonSense,
    })
    return response.data
  } catch (error) {
    return error.data
  }
}