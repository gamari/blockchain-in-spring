import type { NextPage } from "next";
import Head from "next/head";
import Link from "next/link";

const Home: NextPage = () => {
  return (
    <div className="flex justify-center items-center h-screen w-full">
      <Head>
        <title>Create Next App</title>
        <meta name="description" content="Generated by create next app" />
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main>
        <h1>ページ一覧</h1>

        <ul>
          <li>
            <Link href={"/wallet"}>
              <a>仮想ビットコインページ</a>
            </Link>
          </li>
        </ul>
      </main>
    </div>
  );
};

export default Home;
