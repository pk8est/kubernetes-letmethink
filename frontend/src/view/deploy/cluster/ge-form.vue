<template>
  <Form ref="currentForm" :model="form" v-bind="$attrs" :rules="rules" >
      <template v-for="column, index in columns">
        <input v-model="form[column.column]" type="hidden" v-if="column.geType=='hidden'" v-bind="bindColumn(column)" />

        <FormItem :label="column.label !== false && typeof column.label != 'function' ? (column.label ? column.label : column.column) : ''"
          :prop="column.column"
          :label-for="column.column"
          v-else-if="full || column.hide != true"
          v-bind="column.labelProps"
          v-on="column.labelOn" >
            <slot :name="'slot-form-label-' + index" v-if="typeof column.label == 'function'">{{ renderSlot(column.label, 'slot-form-label-' + index) }}</slot>

            <Select v-model="form[column.column]" v-if="(!column.geType || column.geType=='select') && column.options" :element-id="column.column"
              v-bind="bindColumn(column)"
              :disabled="isDisabled(column)"
              v-on="column.on" >
                <Option :value="option.value" v-for="(option, i) in column.options"
                  :disabled="option.disabled"
                  v-bind="option"
                  v-on="option.on">
                    <slot :name="'slot-form-item-' + index + '-' + i">{{ renderSlot(option.label, 'slot-form-item-' + index + '-' + i, option, index) }}</slot>
                </Option>
            </Select>

            <Input v-model="form[column.column]" :type="column.type" v-else-if="!column.geType || column.geType=='input'" :element-id="column.column"
              v-bind="bindColumn(column)"
              :disabled="isDisabled(column)"
              v-on="column.on" />

            <Radio v-model="form[column.column]" v-else-if="column.geType=='radio'" :element-id="column.column"
              v-bind="bindColumn(column)"
              :disabled="isDisabled(column)"
              v-on="column.on"/>

            <RadioGroup v-model="form[column.column]" v-else-if="column.geType=='radioGroup'" :element-id="column.column"
              v-bind="bindColumn(column)"
              :disabled="isDisabled(column)"
              v-on="column.on">
                <Radio v-for="(option, i) in column.options"
                  v-bind="option"
                  :label="option.value"
                  v-on="option.on">
                  <slot :name="'slot-form-item-' + index + '-' + i">{{ renderSlot(option.label != undefined ? option.label : option.value, 'slot-form-item-' + index + '-' + i) }}</slot>
              </Radio>
            </RadioGroup>

            <Checkbox v-model="form[column.column]" v-else-if="column.geType=='checkbox'" :element-id="column.column"
              v-bind="bindColumn(column)"
              :disabled="isDisabled(column)"
              v-on="column.on"/>

            <CheckboxGroup v-model="form[column.column]" v-else-if="column.geType=='checkboxGroup'" :element-id="column.column"
              v-bind="bindColumn(column)"
              :disabled="isDisabled(column)"
              v-on="column.on">
                <Checkbox v-for="(option, i) in column.options"
                  :label="option.value"
                  v-bind="option"
                  v-on="option.on">
                  <slot :name="'slot-form-item-' + index + '-' + i">{{ renderSlot(option.label != undefined ? option.label : option.value, 'slot-form-item-' + index + '-' + i) }}</slot>
                </Checkbox>
            </CheckboxGroup>

            <i-switch v-model="form[column.column]" v-else-if="column.geType=='switch'" :element-id="column.column"
              v-bind="bindColumn(column)"
              :disabled="isDisabled(column)"
              v-on="column.on">
              <span slot="open"><slot :name="'slot-form-item-switch-open-' + index">{{ renderSlot(column.open != undefined ? column.open : '开', 'slot-form-item-switch-open-' + index) }}</slot></span>
              <span slot="close"><slot :name="'slot-form-item-switch-close-' + index">{{ renderSlot(column.close != undefined ? column.close : '关', 'slot-form-item-switch-close-' + index) }}</slot></span>
            </i-switch>

            <AutoComplete v-model="form[column.column]" v-else-if="column.geType=='autoComplete'" :element-id="column.column"
             v-bind="bindColumn(column)"
             :disabled="isDisabled(column)"
             v-on="column.on">
             <Option v-for="(item, i) in column.data" :value="item" :key="item" >
               <slot :name="'slot-form-item-' + index + '-' + i">{{ renderSlot((column.template != undefined ? column.template : item), 'slot-form-item-' + index + '-' + i, item, index) }}</slot>
             </Option>
           </AutoComplete>

        </FormItem>
      </template>
      <slot></slot>
  </Form>

</template>

<script>
import _ from 'lodash'
import GeMixin from './ge-mixin'
export default {
  name: 'GeForm',
  components: {},
  mixins: [GeMixin],
  props: {
    full: true,
    columns: {
      default: () => []
    },
    update: {
      default: () => false
    },
    initForm: {
      default: () => {}
    }

  },
  watch: {
    initForm: function(value){
      this.form = this._initForm()
      this.defaultForm = _.defaultsDeep({}, this.form)
    }
  },
  computed: {
    rules: function(){
      let rules = {}
      this.columns.forEach(column => {
        if(column.rules){
          rules[column.column] = column.rules
        }
      })
      return rules
    }
  },
  data(){
    let form = this._initForm()
    return {
      form,
      defaultForm: _.defaultsDeep({}, form)
    }
  },
  methods: {
    _initForm(){
      let form = {}
      let initForm = _.defaultsDeep({}, this.initForm)
      this.columns.forEach(column => {
        if(initForm[column.column] != undefined){
          form[column.column] = initForm[column.column]
        }else if(column.value != undefined){
          form[column.column] = column.value
        }else{
          form[column.column] = ''
        }

      })
      return form
    },
    bindColumn(column){
      return _.omit(column, ['label', 'column', 'options'])
    },
    isDisabled(column){
      return column.disabled || (this.update && column.disupdated)
    },
    validate(callback){
      this.$refs.currentForm.validate((valid) => {
          callback(valid)
      })
    },
    resetFields(){
      this.$refs.currentForm.resetFields()
    }
  }
}
</script>
