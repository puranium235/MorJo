import axios from 'axios'

export const getKakaoAuth = () => {
  try {
    const KAKAO_AUTH_URL = import.meta.env.VITE_KAKAO_AUTH_URL
    const REST_API_KEY = import.meta.env.VITE_KAKAO_REST_API_KEY
    const REDIRECT_URI = import.meta.env.VITE_KAKAO_REDIRECT_URI

    axios.get(`${KAKAO_AUTH_URL}?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}`)

  } catch (e) {
    console.error(e)
  }
}
