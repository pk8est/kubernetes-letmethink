<template>
  <Form :model="form" v-bind="$attrs">
      <FormItem :label="column.label !== false && typeof column.label != 'function' ? (column.label ? column.label : column.column) : ''"
        :prop="column.column"
        :label-for="column.column"
        v-for="column, index in columns"
        v-if="full || column.hide != true"
        v-bind="column.labelProps"
        v-on="column.labelOn" >
          <slot :name="'slot-form-label-' + index" v-if="typeof column.label == 'function'">{{ renderItem(column.label, 'slot-form-label-' + index) }}</slot>

          <Select v-model="form[column.column]" v-if="(!column.geType || column.geType=='select') && column.options" :element-id="column.column"
            :disabled="column.disabled"
            :multiple="column.multiple"
            :size="column.size"
            :placeholder="column.placeholder"
            :filterable="column.filterable"
            v-bind="column.props"
            v-on="column.on" >
              <Option :value="option.value" v-for="(option, i) in column.options"
                :disabled="option.disabled"
                v-bind="option.props"
                v-on="option.on">
                  <slot :name="'slot-form-item-' + index + '-' + i">{{ renderItem(option.label, 'slot-form-item-' + index + '-' + i, option, index) }}</slot>
              </Option>
          </Select>

          <Input v-model="form[column.column]" :type="column.type" v-else-if="!column.geType || column.geType=='input'" :element-id="column.column"
            :size="column.size"
            :disabled="column.disabled"
            :placeholder="column.placeholder"
            :readonly="column.readonly"
            :rows="column.rows"
            :number="column.number"
            v-bind="column.props"
            v-on="column.on" />

          <Radio v-model="form[column.column]" v-else-if="column.geType=='radio'" :element-id="column.column"
            :size="column.size"
            :disabled="column.disabled"
            :true-value="column.trueValue"
            :false-value="column.falseValue"
            v-bind="column.props"
            v-on="column.on"/>

          <RadioGroup v-model="form[column.column]" v-else-if="column.geType=='radioGroup'" :element-id="column.column"
            :size="column.size"
            :type ="column.type"
            :vertical="column.vertical"
            :disabled="column.disabled"
            v-bind="column.props"
            v-on="column.on">
              <Radio v-for="(option, i) in column.options"
                :label="option.value"
                :size="option.size"
                :disabled="option.disabled"
                :true-value="option.trueValue"
                :false-value="option.falseValue"
                v-bind="option.props"
                v-on="option.on">
                <slot :name="'slot-form-item-' + index + '-' + i">{{ renderItem(option.label != undefined ? option.label : option.value, 'slot-form-item-' + index + '-' + i) }}</slot>
            </Radio>
          </RadioGroup>

          <Checkbox v-model="form[column.column]" v-else-if="column.geType=='checkbox'" :element-id="column.column"
            :label="column.label"
            :size="column.size"
            :disabled="column.disabled"
            :indeterminate="column.indeterminate"
            :true-value="column.trueValue"
            :false-value="column.falseValue"
            v-bind="column.props"
            v-on="column.on"/>

          <CheckboxGroup v-model="form[column.column]" v-else-if="column.geType=='checkboxGroup'" :element-id="column.column"
            :size="column.size"
            v-bind="column.props"
            v-on="column.on">
              <Checkbox v-for="(option, i) in column.options"
                :label="option.value"
                :size="option.size"
                :disabled="option.disabled"
                :indeterminate="option.indeterminate"
                :true-value="option.trueValue"
                :false-value="option.falseValue"
                v-bind="option.props"
                v-on="option.on">
                <slot :name="'slot-form-item-' + index + '-' + i">{{ renderItem(option.label != undefined ? option.label : option.value, 'slot-form-item-' + index + '-' + i) }}</slot>
              </Checkbox>
          </CheckboxGroup>

          <i-switch v-model="form[column.column]" v-else-if="column.geType=='switch'" :element-id="column.column"
            :size="column.size"
            :disabled="column.disabled"
            :loading="column.loading"
            :true-value="column.trueValue"
            :false-value="column.falseValue"
            v-bind="column.props"
            v-on="column.on">
            <span slot="open"><slot :name="'slot-form-item-switch-open-' + index">{{ renderItem(column.open != undefined ? column.open : '开', 'slot-form-item-switch-open-' + index) }}</slot></span>
            <span slot="close"><slot :name="'slot-form-item-switch-close-' + index">{{ renderItem(column.close != undefined ? column.close : '关', 'slot-form-item-switch-close-' + index) }}</slot></span>
          </i-switch>

          <AutoComplete v-model="form[column.column]" v-else-if="column.geType=='autoComplete'" :element-id="column.column"
           :data="column.data"
           :size="column.size"
           :disabled="column.disabled"
           :filter-method	="column.filterMethod"
           :placeholder="column.placeholder"
           v-bind="column.props"
           v-on="column.on">
           <Option v-for="(item, i) in column.data" :value="item" :key="item" >
             <slot :name="'slot-form-item-' + index + '-' + i">{{ renderItem((column.template != undefined ? column.template : item), 'slot-form-item-' + index + '-' + i, item, index) }}</slot>
           </Option>
         </AutoComplete>
      </FormItem>
      <slot></slot>
  </Form>

</template>

<script>
export default {
  name: 'GeForm',
  components: {},
  props: {
      columns: {
          default: () => []
      },
      full: true
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
    renderItem(fn, name, ...args){
      this.$slots[name] = typeof fn == 'function' ? fn(this.$createElement, ...args) : fn
    }
  }
}
</script>
