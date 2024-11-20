import api from '@/api/api.js'

export const postJoin = async (nickname) => {
  try {
    const response = await api.post("/join", { nickname })
    return response.data;
  } catch (e) {
    console.error(e)
    throw e
  }
}
