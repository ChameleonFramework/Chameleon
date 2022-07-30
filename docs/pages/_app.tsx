import 'nextra-theme-docs/style.css';
import type { AppProps } from 'next/app';

function Chameleon({ Component, pageProps }: AppProps) {
  const getLayout = (Component as any).getLayout || ((page: any) => page);
  return getLayout(<Component {...pageProps} />);
}

export default Chameleon
