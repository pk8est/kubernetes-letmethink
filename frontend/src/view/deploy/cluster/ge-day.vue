<script>
import dayjs from 'dayjs'
export default {
  name: 'GeDay',
  props: {
    value: '',
    tag: {
      default: () => 'span'
    },
    format: {
      default: () => 'YYYY-MM-DD HH:mm:ss'
    }
  },
  render(h) {
    return h(this.tag, this.$props, dayjs(this.value).format('YYYY-MM-DD HH:mm:ss'))
  }
}
</script>
