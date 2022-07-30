const withNextra = require('nextra')({
  theme: 'nextra-theme-docs',
  themeConfig: './theme.config.js',
  unstable_flexsearch: true,
  unstable_staticImage: true
});

module.exports = withNextra({
  reactStrictMode: true,
  redirects: () => {
    return [
      {
        source: '/support',
        destination: 'https://discord.hypera.dev/',
        permanent: false
      }
    ]
  }
});