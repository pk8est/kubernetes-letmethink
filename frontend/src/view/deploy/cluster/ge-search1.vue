<script>
import _ from 'lodash'

const pickField = ['class', 'style', 'attrs', 'props', 'domProps', 'on', 'nativeOn', 'directives', 'scopedSlots', 'slot', 'key', 'ref', 'refInFor']

export default {
  name: 'GeSearch',
  components: { },
  props: {
      columns: {
        default: () => []
      },
      searchButton: {
        default: () => (h) => <Button type="primary">搜索</Button>
      },
      unsetButton: {
        default: () => (h) => <Button type="primary" style="margin-left: 8px">重置</Button>
      },
      createButton: {
        default: () => (h) => <Button type="success" style="margin-left: 8px">新建</Button>
      }
  },
  computed: {
    form: function() {
      let _form = {}
      this.columns.forEach(field => _form[field.column] = field.value)
      return _form
    }
  },
  render(h) {
    return (
      <div>
        <Form v-model={ this.form } inline label-position="left" label-width= { 60 }>
          { this.columns.map( (field) => this.renderFormItem(h, field) ) }
          { this.renderButton(h) }
        </Form>
      </div>
    )
  },
  methods: {
    renderItem(h, fn, ...args){
      return typeof fn == 'function' ? fn(h, args) : fn
    },
    renderFormItem(h, field) {
      let children = [];
      let { el, value, options, type } = field

      if(!el && options){
        el = 'Select'
        children = options.map((option) => {
          return h('Option', {
            ..._.defaultsDeep({
              props: {
                value: option.value,
                disabled: option.disabled
              }
            }, {..._.omit(option, ['value', 'label', 'disabled'])})
          }, [this.renderItem(h, option.label)])
        })
      }else if(!el){
        el = 'Input'
      }
      if(!type && el == 'Input'){
        type == 'text'
      }
      return h('FormItem', _.defaultsDeep(_.pick(field, pickField), {
        props: {
          label: this.renderItem(h, field.label)
        },
        style: {
          width: "250px"
        }
      }), [
        h(el, _.defaultsDeep(_.pick(field, pickField), {
          props: {
            type,
            value
          }
        }), children)
      ])
    },
    renderButton(h) {
      return (
        <FormItem>
          { this.searchButton && this.searchButton(h) }
          { this.unsetButton && this.unsetButton(h) }
          { this.createButton && this.createButton(h) }
        </FormItem>
      )
    }
  }
}
</script>
