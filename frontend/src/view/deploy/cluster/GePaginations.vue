<template>
    <Page :total="total" show-sizer show-elevator />
</template>

<script>
export default {
  name: 'GePaginations',
  components: { },
  props: {
      total: {
          default: () => 15
      }
  }
}
</script>
