import axios from '@/libs/deploy.api.request'

const MODULE_NAME = 'cluster'

export default {
  list(params) {
    return axios.request({
      url: MODULE_NAME + '/list',
      params
    })
  },
  get(id) {
    return axios.request({
      url: MODULE_NAME + '/get/' + id
    })
  }
}
