<template>
  <Form :model="form" inline label-position="left" :label-width="80">
      <FormItem :label="column.label !== false && typeof column.label != 'function' ? (column.label ? column.label : column.column) : ''" v-for="column in columns">
        <slot v-if="typeof column.label == 'function'">{{ renderItem(column.label) }}</slot>

        <Select v-model="form[column.column]" v-if="(!column.geType || column.geType=='select') && column.options"
          :disabled="column.disabled"
          :multiple="column.multiple"
          :size="column.size"
          :placeholder="column.placeholder"
          :filterable="column.filterable"
          v-bind="column.props"
          v-on="column.on" >
            <Option :value="option.value" v-for="(option, index) in column.options"
              :disabled="option.disabled"
              v-bind="option.props"
              v-on="option.on">
                <slot>{{ renderItem(option.label, option, index) }}</slot>
            </Option>
        </Select>

        <Input v-model="form[column.column]" :type="column.type" v-else-if="!column.geType || column.geType=='input'"
          :size="column.size"
          :disabled="column.disabled"
          :placeholder="column.placeholder"
          :readonly="column.readonly"
          :rows="column.rows"
          :number="column.number"
          v-bind="column.props"
          v-on="column.on" />

        <Radio v-model="form[column.column]" v-else-if="column.geType=='radio'"
          :size="column.size"
          :disabled="column.disabled"
          :true-value="column.trueValue"
          :false-value="column.falseValue"
          v-bind="column.props"
          v-on="column.on"/>

        <RadioGroup v-model="form[column.column]" v-else-if="column.geType=='radioGroup'"
          :size="column.size"
          :type ="column.type"
          :vertical="column.vertical"
          :disabled="column.disabled"
          v-bind="column.props"
          v-on="column.on">
            <Radio v-for="(option, index) in column.options"
              :label="option.value"
              :size="option.size"
              :disabled="option.disabled"
              :true-value="option.trueValue"
              :false-value="option.falseValue"
              v-bind="option.props"
              v-on="option.on">
              <slot>{{ renderItem(option.label != undefined ? option.label : option.value) }}</slot>
          </Radio>
        </RadioGroup>

        <Checkbox v-model="form[column.column]" v-else-if="column.geType=='checkbox'"
          :label="column.label"
          :size="column.size"
          :disabled="column.disabled"
          :indeterminate="column.indeterminate"
          :true-value="column.trueValue"
          :false-value="column.falseValue"
          v-bind="column.props"
          v-on="column.on"/>

        <CheckboxGroup v-model="form[column.column]" v-else-if="column.geType=='checkboxGroup'"
          :size="column.size"
          v-bind="column.props"
          v-on="column.on">
            <Checkbox v-for="(option, index) in column.options"
              :label="option.value"
              :size="option.size"
              :disabled="option.disabled"
              :indeterminate="option.indeterminate"
              :true-value="option.trueValue"
              :false-value="option.falseValue"
              v-bind="option.props"
              v-on="option.on">
              <slot>{{ renderItem(option.label != undefined ? option.label : option.value) }}</slot>
            </Checkbox>
        </CheckboxGroup>

        <i-switch v-model="form[column.column]" v-else-if="column.geType=='switch'"
          :size="column.size"
          :disabled="column.disabled"
          :loading="column.loading"
          :true-value="column.trueValue"
          :false-value="column.falseValue"
          v-bind="column.props"
          v-on="column.on">
          <span slot="open"><slot>{{ renderItem(column.open != undefined ? column.open : '开') }}</slot></span>
          <span slot="close"><slot>{{ renderItem(column.close != undefined ? column.close : '关') }}</slot></span>
        </i-switch>

        <AutoComplete v-model="form[column.column]" v-else-if="column.geType=='autoComplete'"
         :data="column.data"
         :size="column.size"
         :disabled="column.disabled"
         :filter-method	="column.filterMethod"
         :placeholder="column.placeholder"
         v-bind="column.props"
         v-on="column.on">
         <Option v-for="(item, index) in column.options" :value="item" :key="item" >
           <slot>{{ renderItem((column.optionTemplate != undefined ? column.optionTemplate : item), item, index) }}</slot>
         </Option>
       </AutoComplete>

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
    form: {
      get: function(){
        let _form = {}
        this.columns.forEach(column => _form[column.column] = column.value)
        return _form
      }
    }
  },
  methods: {
    renderItem(fn, ...args){
      this.$slots.default = typeof fn == 'function' ? fn(this.$createElement, ...args) : fn
    }
  }
}
</script>
