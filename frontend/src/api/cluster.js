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
  },
  create(data) {
    return axios.request({
      url: MODULE_NAME + '/create',
      method: 'post',
      data
    })
  },
  update(id, data) {
    return axios.request({
      url: MODULE_NAME + '/update/' + id,
      method: 'put',
      data
    })
  },
  delete(id) {
    return axios.request({
      url: MODULE_NAME + '/delete/' + id,
      method: 'delete'
    })
  }
}
