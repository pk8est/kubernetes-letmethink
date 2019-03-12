<template>
    <div>
      <Table :columns="showColumns" :data="data" v-bind="$attrs"
      @on-sort-change="sortChangeHandler"
      v-on="on" />
      <Row style="margin-top: 10px">
        <Col span="23">
          <Page :total="total"
            show-sizer
            show-elevator
            :page-size-opts="pageSizeOpts"
            @on-change="pageChangeHandler"
            @on-page-size-change="pageSizeChangeHandler" />
        </Col>
        <Col span="1">
          <Icon type="md-settings" @click="settingDrawer = true" size="24"/>
          <Drawer title="设置" :closable="false" v-model="settingDrawer">
              <p v-for="column in setting"><Checkbox v-model="column.show">{{ column.title }}</Checkbox></p>
          </Drawer>
        </Col>
      </Row>
    </div>
</template>

<script>
export default {
  name: 'GeListgrid',
  components: { },
  props: {
      columns: {
          default: () => []
      },
      total: 0,
      defaultShow: {
        default: () => true
      },
      data: {
          default: () => []
      },
      pageSizeOpts: {
          default: () => [10, 20, 50, 100]
      },
      on: {
          default: () => {}
      }
  },
  data(){
    return {
      settingDrawer: false,
      setting: this.initSetting()
    }
  },
  computed: {
    showColumns(){
      return this.columns.filter((column) => {
        return !this.setting[column.key] || this.setting[column.key].show == true
      })
    }
  },
  methods: {
    initSetting(){
      let setting = {}
      this.columns.map((column) => {
        if(column.key){
          setting[column.key] = {
            show: column.show ? column.show : this.defaultShow,
            title: column.title
          }
        }
      })
      return setting
    },
    sortChangeHandler(column){
      this.$emit("sort-change", column)
    },
    pageChangeHandler(page){
      this.$emit("page-change", page)
    },
    pageSizeChangeHandler(size){
      this.$emit("page-size-change", size)
    }
  }
}
</script>
