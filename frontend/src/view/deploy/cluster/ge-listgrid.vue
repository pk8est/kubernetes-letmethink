<template>
    <Table :columns="columns" :data="data"></Table>
</template>

<script>
export default {
  name: 'GeListgrid',
  components: { },
  props: {
      columns: {
          default: () => []
      },
      data: {
          default: () => []
      }
  },
  data: function () {
    return {
      count: 0
    }
  }
}
</script>
