<template>
  <Row>
    <Col span="22">
      <GeForm ref="form" :columns="columns" :initForm="initForm" :label-width="100" :update="update" full="true" v-bind="$attrs"></GeForm>
    </Col>
  </Row>
</template>

<script>
import _ from 'lodash'
import emitter from 'iview/src/mixins/emitter'
import GeForm from './ge-form'
export default {
  name: 'GeModalForm',
  components: { GeForm },
  mixins: [emitter],
  props: {
      value: {
          default: () => false
      },
      columns: {
          default: () => []
      },
      update: {
        default: () => false
      },
      initForm: {
          default: () => {}
      },
      on: {
          default: () => {}
      }
  },
  data(){
    return {

    }
  },
  created () {
    this.submit()
  },
  methods: {
    submit () {
      this.$on('submit', () => {
        this.$refs.form.validate((valid) => {
            if (valid) {
              this.dispatch('GeIndex', 'recieve', {
                valid: true,
                update: this.update,
                data: this.$refs.form.form
              })
            }
        })
      })
    }
  }
}
</script>
