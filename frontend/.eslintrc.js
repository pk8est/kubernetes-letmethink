module.exports = {
  root: true,
  'extends': [
    'plugin:vue/essential',
    '@vue/standard'
  ],
  rules: {
    'no-return-assign': 0,
    'no-console': 0,
    'no-debugger': 0,
    'no-cond-assign': 0,
    'space-before-function-paren': ['error', 'always'],
    'arrow-parens': 0,
    // allow async-await
    'generator-star-spacing': 0,
    // allow debugger during development
    'indent': ['error', 2],
    'require-await': 'error',
    'semi': ['error',
      'never',
      {
        'beforeStatementContinuationChars': 'always'
      }
    ],
    'vue/html-self-closing': 0,
    'no-mixed-operators': 0,
    'vue/valid-v-for': 0,
    'vue/no-use-v-if-with-v-for': 0,
    'vue/html-indent': 0,
    'vue/max-attributes-per-line': 0,
    'vue/no-parsing-error': 0,
    'operator-linebreak': 0
  },
  parserOptions: {
    parser: 'babel-eslint'
  }
}
