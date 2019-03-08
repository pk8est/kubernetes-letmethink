<template>
  <Form :model="form" inline label-position="left" :label-width="80">
      <FormItem :label="typeof column.label != 'function' ? (column.label ? column.label : column.column) : ''" v-for="column in columns">
        <slot v-if="typeof column.label == 'function'">{{ renderItem(column.label) }}</slot>
        <!--<Node :node="column.label" v-if="typeof column.label == 'function'"></Node>-->
        <Select v-model="form[column.column]" v-if="(!column.type || column.type=='select') && column.options" :disabled="column.disabled" v-bind="column.props" v-on="column.on">
            <Option :value="option.value" v-for="(option, index) in column.options" :disabled="option.disabled" v-bind="option.props" v-on="option.on">
              <!--<Node :node="option.label"></Node>-->
              <!--<slot :name="'select-option-' + column.type + '-' + index">{{ renderItem(option.label, 'select-option-' + column.type + '-' + index) }}</slot>-->
              <slot>{{ renderItem(option.label, option, index) }}</slot>
            </Option>
        </Select>

        <Input v-model="form[column.column]" type="text" v-else-if="!column.type || column.type=='type'" :disabled="column.disabled" v-bind="column.props" v-on="column.on"/>

        <Radio v-model="form[column.column]" v-else-if="column.type=='radio'"
          :size="column.size"
          :disabled="column.disabled"
          :true-value="column.trueValue"
          :false-value="column.falseValue"
          v-bind="column.props"
          v-on="column.on"/>

        <RadioGroup v-model="form[column.column]" v-else-if="column.type=='radioGroup'"
          :size="column.size"
          :type ="column.button"
          :vertical="column.vertical"
          :disabled="column.disabled"
          v-bind="column.props"
          v-on="column.on">
            <Radio v-model="form[column.column]" v-for="(option, index) in column.options"
              :label="option.label"
              :size="option.size"
              :disabled="option.disabled"
              :true-value="option.trueValue"
              :false-value="option.falseValue"
              v-bind="option.props"
              v-on="option.on"/>
          </Radio>
        </RadioGroup>

        <Checkbox v-model="form[column.column]" v-else-if="column.type=='checkbox'"
          :label="column.label"
          :size="column.size"
          :disabled="column.disabled"
          :indeterminate="column.indeterminate"
          :true-value="column.trueValue"
          :false-value="column.falseValue"
          v-bind="column.props"
          v-on="column.on"/>

        <CheckboxGroup v-model="form[column.column]" v-else-if="column.type=='checkboxGroup'"
          :size="column.size"
          v-bind="column.props"
          v-on="column.on">
            <Checkbox v-model="form[column.column]" v-for="(option, index) in column.options"
              :label="option.label"
              :size="option.size"
              :disabled="option.disabled"
              :indeterminate="option.indeterminate"
              :true-value="option.trueValue"
              :false-value="option.falseValue"
              v-bind="option.props"
              v-on="option.on"/>
            </Radio>
        </CheckboxGroup>

      </FormItem>
      <FormItem>
        <Button type="primary">搜索</Button>
        <Button type="primary" style="margin-left: 8px">重置</Button>
        <Button type="success" style="margin-left: 8px">新建</Button>
      </FormItem>
  </Form>

</template>

<script>
import Node from './node'
export default {
  name: 'GeSearch',
  components: { Node },
  props: {
      columns: {
          default: () => []
      }
  },
  computed: {
    form: function() {
      let _form = {}
      this.columns.forEach(column => _form[column.column] = column.value)
      return _form
    }
  },
  methods: {
    /*renderItem(fn, name, ...args){
      this.$slots[name] = typeof fn == 'function' ? fn(this.$createElement, args) : fn
    },*/
    renderItem(fn, ...args){
      this.$slots.default = typeof fn == 'function' ? fn(this.$createElement, args) : fn
    }
  }
}
</script>
