import defaultsDeep from 'lodash/defaultsDeep'

export default {
  name: "GeMixin",
  methods: {
    renderSlot(fn, name, ...args){
      this.$slots[name] = typeof fn == 'function' ? fn(this.$createElement, ...args) : fn
    },
    emitEvents(targets, name, ...args){
      for(let target of targets) this.emitEvent(target, name, ...args)
    },
    emitEvent(target, name, ...args){
      if(target[name]){
        if(typeof target[name] === 'function'){
          target[name](...args)
        }else{
          console.warn('Event ['+name+'] not is function!');
        }
      }
    }
  }

}
