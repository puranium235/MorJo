import api from '@/api/api.js'

export const postJoin = async (nickname) => {
  try {
    const response = await api.post('/user/join', { nickname })
    return response.data
  } catch (e) {
    console.error(e)
    throw e
  }
}

export const getIsLoggedIn = async () => {
  try {
    const response = await api.get('/user')
    return response.data
  } catch (e) {
    console.error(e)
    throw e
  }
}

export const postLogout = async () => {
  try {
    const response = await api.post('/logout')
    return response.data
  } catch (e) {
    console.error(e)
  }
}
