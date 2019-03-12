<template>
  <Row>
    <Col span="21">
      <GeForm ref="searchForm" :columns="columns" inline label-position="left" :label-width="50" :full="full"></GeForm>
    </Col>

    <Col span="3">
      <Button type="success" @click="$emit('search', $refs.searchForm.form)"><Icon type="md-search" />搜索</Button>
      <Button type="primary" style="margin-left: 8px" @click="resetHandler">重置</Button>
      <a @click="full=!full" style="margin-left: 18px">
        <span v-show="!full"><Icon type="ios-arrow-dropdown" size="16"/>更多</span>
        <span v-show="full"><Icon type="ios-arrow-dropleft" size="16"/>收起</span>
      </a>
    </Col>
  </Row>
</template>

<script>
import _ from 'lodash'
import GeForm from './ge-form'
export default {
  name: 'GeSearch',
  components: { GeForm },
  props: {
      columns: {
          default: () => []
      }
  },
  data(){
    return {
      full: false
    }
  },
  methods: {
    resetHandler(){
      this.$refs.searchForm.form = _.defaultsDeep({}, this.$refs.searchForm.defaultForm)
      this.$emit('reset', this.$refs.searchForm.defaultForm)
    }
  }
}
</script>
