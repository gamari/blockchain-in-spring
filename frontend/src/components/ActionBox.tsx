import React from "react";
import { useRecoilState } from "recoil";
import { blockchain_atom } from "../atoms/BlockChainAtoms";
import { wallet_amount_atom } from "../atoms/WalletAtoms";
import { amount_url, chain_url, mining_url } from "../constants/ulrs";
import { fetch_chain, get_amount } from "../libs/FetchApi";

type Props = {
  address: string;
};

const ActionBox: React.FC<Props> = ({ address }) => {
  const [amount, setAmount] = useRecoilState(wallet_amount_atom);
  const [chain, setChain] = useRecoilState(blockchain_atom);

  const reload_amount = async () => {
    const amount = await get_amount(address, amount_url);
    setAmount(amount);
    alert(`残高の更新をしました[残高=${amount}]`);
  };

  const handleMining = async () => {
    const response = await fetch(mining_url, {
      method: "GET",
    });

    await response.json();

    alert("マイニングが完了しました。");
  };

  const handleGetChain = async () => {
    const chain = await fetch_chain(chain_url);
    setChain(chain);
  };

  return (
    <div>
      <h1 className="font-bold text-2xl">操作</h1>
      <p className="border-[1px] border-gray-500 p-2 flex flex-row space-x-2">
        <button
          className="btn btn-gray"
          onClick={() => {
            handleMining();
          }}
        >
          1⃣手動でマイニング
        </button>

        <button
          className="btn btn-gray"
          onClick={() => {
            handleGetChain();
          }}
        >
          2⃣ブロック情報を更新
        </button>
        <button
          className="btn btn-gray"
          onClick={() => {
            reload_amount();
          }}
        >
          3⃣残高を更新する
        </button>
      </p>
    </div>
  );
};

export default ActionBox;
