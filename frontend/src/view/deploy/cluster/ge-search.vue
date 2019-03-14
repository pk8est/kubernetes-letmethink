<template>
  <Row>
    <div style="float: right">
      <a @click="full=!full">
        <span v-show="!full"><Icon type="ios-arrow-dropdown" size="16"/>更多</span>
        <span v-show="full"><Icon type="ios-arrow-dropleft" size="16"/>收起</span>
      </a>
      <Button type="primary" style="margin-left: 18px" @click="$emit('search', $refs.searchForm.form)">搜索</Button>
      <Button style="margin-left: 8px" @click="resetHandler">重置</Button>
      <slot></slot>
    </div>
    <GeForm ref="searchForm" :columns="columns" inline label-position="left" :label-width="50" :full="full"></GeForm>
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
