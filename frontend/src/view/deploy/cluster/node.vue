<template>
  <span><slot> {{ renderItem(this.node) }} </slot></span>
</template>
<script>
export default {
  name: 'Node',
  props: ['node'],
  methods: {
    renderItem(fn, ...args){
      this.$slots.default = typeof fn == 'function' ? fn(this.$createElement, args) : fn
    }
  }
}
</script>
