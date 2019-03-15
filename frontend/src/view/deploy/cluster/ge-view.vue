<template>
  <div>
    <p v-for="(column, index) in columns" class="view">
      <Row :gutter="20">
          <Col span="4" class="title" align="right">
            <slot :name="'slot-view-title-' + index">
              {{ column.renderHeader ? renderSlot(column.renderHeader, 'slot-view-title-' + index, { key: column.key, column, index }) : column.title }}
            </slot>
          </Col>
          <Col span="20">
            <slot :name="'slot-view-' + index">
              {{ column.render ? renderSlot(column.render, 'slot-view-' + index, { key: column.key, column, index, row: data, value: data[column.key] }) : data[column.key] }}
            </slot>
          </Col>
      </Row>
    </p>
  </div>
</template>

<script>
import _ from 'lodash'
import emitter from 'iview/src/mixins/emitter'
import GeMixin from './ge-mixin'
import GeForm from './ge-form'
export default {
  name: 'GeModalForm',
  components: { GeForm },
  mixins: [emitter, GeMixin],
  props: {
      data: {
          default: () => {}
      },
      columns: {
          default: () => []
      },
      on: {
          default: () => {}
      }
  },
  data(){
    return {

    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
p.view{
  font-size: 13px;
  margin-top: 8px;
  margin-bottom: 8px;
  .title{
    font-weight: bold;
  }
}
</style>
