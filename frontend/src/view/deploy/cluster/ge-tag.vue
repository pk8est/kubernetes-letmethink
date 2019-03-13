<script>
export default {
  name: 'GeTag',
  props: {
    value: '',
    options: {
      default: () => []
    }
  },
  render(h) {
    for(let option of this.options){
      if(option.value == this.value){
        return (<Tag
          closable={ option.closable }
          checkable={ option.checkable }
          checked={ option.checked }
          type={ option.type }
          color={ option.color }
          name={ option.name }
          fade={ option.fade }
          > { option.label } </Tag>)
      }
    }
  }
}
</script>
