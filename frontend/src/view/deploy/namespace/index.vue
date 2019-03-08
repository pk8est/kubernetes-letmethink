<template>
  <div class="config-main">
    <Card>
      <p slot="title">
        <Icon type="ios-card-outline" size="24"></Icon>
        查询条件
      </p>
      <Form  ref="searchForm" :model="searchForm"  :label-width="50">
        <Row :gutter="20">
          <Col span="6">
            <FormItem label="名称" >
              <Input v-model="searchForm.configName" placeholder /></Input>
            </FormItem>
          </Col>
          <Col span="6">
            <Button type="primary" @click="search" style="margin-right: 16px;">搜索</Button>
            <Button type="primary" @click="resetSearch" style="margin-right: 16px;">重置</Button>
            <Button type="success" @click="newConfiguration" style="margin-right: 16px;">新建</Button>
          </Col>
        </Row>
      </Form>

    </Card>
    <Divider />
    <Row>
      <Table border :columns="columns" :data="data.list" :loading="tableLoading"/>
    </Row>

    <Modal
      v-model="create"
      title="创建"
      :width="840"
    >
      <Form  ref="form" :model="form" :rules="rules" :label-width="100">
        <FormItem label="分组名" prop="name">
          <Select type="text" v-model="copyForm.targetSetId">
            <Option  v-for="(setInfo, setIndex) in setList" :key="setIndex" :value="setInfo.id">{{setInfo.name}}</Option>
          </Select>
        </FormItem>
        <FormItem label="原配置名" prop="configName">
          <Input v-model="copyForm.configName" placeholder disabled/></Input>
        </FormItem>
        <FormItem label="目标配置名" prop="configName">
          <Input v-model="copyForm.targetConfigName" placeholder/></Input>
        </FormItem>

        <FormItem label="备注" prop="remark">
          <Input v-model="copyForm.remark" placeholder/></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="success" size="large" @click="submitCopy">提交</Button>
      </div>
    </Modal>
  </div>
</template>

<script type="text/ecmascript-6">
import { getList } from '@api/configurationSet'
import { getListAll, deleteConfig, getConfigHistory, copyConfig, search } from '@api/configuration'
import { mapGetters } from 'vuex'
import dayjs from 'dayjs'
export default {
  components: {},
  methods: {
    resetSearch () {
      this.$refs['searchForm'].resetFields()
      this.initList()
    },
    search () {
      this.tableLoading = true
      search(this.searchForm).then((data) => {
        this.data.list = data.result
        this.tableLoading = false
      })
    },
    initList () {
      this.tableLoading = true
      getListAll(this.project.projectId).then(({ result }) => {
        this.data.list = result
        this.tableLoading = false
      })
    },
    initSetList () {
      getList(this.project.projectId).then(({ result }) => {
        this.setList = result
      })
    },
    newConfiguration () {
      this.$router.push({
        name: 'configForm'
      })
    },
    getHistoryList (projectId, configName) {
      getConfigHistory(projectId, configName).then((data) => {
        this.configHistory = true
        this.historyData = data.result
      })
    },
    submitCopy () {
      copyConfig(this.copyForm).then((data) => {
        this.copyModal = false
        this.initList()
      })
    }

  },

  computed: {
    ...mapGetters({
      project: 'GET_PROJECT'
    })
  },

  watch: {
    project (newVal) {
      this.initList()
      this.initSetList()
    }
  },
  data () {
    return {

      configHistory: false,
      copyModal: false,
      tableLoading: false,
      setList: [],
      copyForm: {
        configId: '',
        targetSetId: '',
        configName: '',
        targetConfigName: '',
        remark: ''
      },
      searchForm: {
        configName: '',
        setId: 0,
        exact: false
      },

      copyFormRule: {
        name: [{ required: true, trigger: 'blur', message: '请输入名称' }],
        configName: [{ required: true, trigger: 'blur', message: '请输入英文名称', pattern: /^[a-z0-9]([-a-z0-9]*[a-z0-9])?$/ }]
      },
      historyColumns: [
        {
          title: '配置名',
          key: 'configName'
        },
        {
          title: '版本',
          key: 'currentVersion',
          render: (h, { row }) => {
            return (
              <span>{ row.configVersionPrefix || 'v'}{row.configVersion}</span>
            )
          }
        },
        {
          title: '操作人',
          key: 'opUser'
        },
        {
          title: '修改时间',
          key: 'updateTime',
          render: (h, { row }) => {
            return (
              <span>{ dayjs(row.updateTime).format('YYYY-MM-DD HH:mm:ss') }</span>
            )
          }
        },
        {
          title: '备注',
          key: 'remark'
        }
      ],
      historyData: [],
      columns: [

        {
          title: '分组名',
          key: 'setName'
        },
        {
          title: '配置名',
          key: 'configName'
        },
        {
          title: '当前版本',
          key: 'currentVersion'
        },
        {
          title: '操作人',
          key: 'opUser'
        },
        {
          title: '修改时间',
          key: 'updateTime',
          render: (h, { row }) => {
            return (
              <span>{ dayjs(row.updateTime).format('YYYY-MM-DD HH:mm:ss') }</span>
            )
          }
        },
        {
          title: '引用实例数',
          key: 'configRefCount'
        },
        {
          title: '备注',
          key: 'remark'
        },
        {
          title: '操作',
          key: 'action',
          width: 400,
          align: 'left',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.$router.push({
                      name: 'configForm',
                      query: {
                        id: params.row.id
                      }
                    })
                  }
                }
              }, '查看/变更'),
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.copyForm.configId = params.row.id
                    this.copyForm.configName = params.row.configName
                    this.copyModal = true
                  }
                }
              }, '复制配置'),
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.getHistoryList(params.row.projectId, params.row.configName)
                  }
                }
              }, '版本历史'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                on: {
                  click: () => {
                    console.log('当前引用数目：', params.row.configRefCount)
                    if (params.row.configRefCount === 0) {
                      this.$Modal.confirm({
                        render: (h) => {
                          return h('Input', {
                            props: {
                              value: this.value,
                              autofocus: true,
                              placeholder: '请输入删除理由'
                            },
                            on: {
                              input: (val) => {
                                this.value = val
                              }
                            }
                          })
                        },
                        onOk: () => {
                          if (this.value === '') {
                            this.$Modal.confirm({
                              title: '提示',
                              content: '<p>理由不能为空</p>',
                              okText: 'OK',
                              cancelText: 'Cancel'
                            })
                          } else {
                            deleteConfig(params.row.id).then(data => {
                              if (data.code >= 0) {
                                this.initList()
                              }
                            })
                          }
                        }
                      })
                    } else {
                      this.$Modal.confirm({
                        title: '提示',
                        content: '当前配置存在' + params.row.configRefCount + '份引用，不能删除'
                      })
                    }
                  }
                }
              }, '删除')
            ])
          }
        }
      ],
      data: {
        list: []
      }
    }
  },
  mounted () {
    this.initList()
    this.initSetList()
  },
  created () {
    // this.initList()
  }

}

</script>

<style scoped lang=scss>
  .layout-header-bar{
    background: #fff;
    box-shadow: 0 1px 1px rgba(0,0,0,.1);
  }
</style>
