import api from '@/api/api.js'

export const postJoin = async (nickname) => {
  try {
    const response = await api.post('/user/join', { nickname })
    return response.data
  } catch (e) {
    const errorMessage = {
      400: '잘못된 요청입니다',
      401: '카카오 로그인을 통해 회원가입을 시도해주세요',
      409: '중복된 닉네임입니다',
      unknown: '알 수 없는 에러가 발생했습니다',
    }

    if (e.response) {
      const { status } = e.response
      throw new Error(`${errorMessage[status]}`)
    }
    throw new Error(`${errorMessage.unknown}`)
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
