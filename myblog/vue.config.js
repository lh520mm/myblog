module.exports = {
    publicPath: process.env.NODE_ENV === 'production'
    ? '/myblog/'
    : '/',
    lintOnSave: true,
    productionSourceMap: false,
    devServer: {
        port: 8088,
        host: "localhost",
        https: false,
        open: true
    }

}