<template>
  <GeForm ref="searchForm" :columns="columns" inline label-position="left" :label-width="60">
      <FormItem>
        <Button type="primary" @click="search">搜索</Button>
        <Button type="primary" style="margin-left: 8px">重置</Button>
        <Button type="success" style="margin-left: 8px">新建</Button>
      </FormItem>
  </GeForm>

</template>

<script>
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
      test: "ssss"
    }
  },
  methods: {
    search(){
      const form = this.$refs.searchForm.form
    }
  }
}
</script>
