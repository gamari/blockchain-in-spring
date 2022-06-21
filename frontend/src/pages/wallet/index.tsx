import React, { useEffect, useState } from "react";
import BlockChainInformation from "../../components/BlockChainInformation";
import Footer from "../../components/Footer";
import SendMoney from "../../components/SendMoney";
import WalletInformation from "../../components/WalletInformation";
import { amount_url, chain_url, wallet_url } from "../../constants/ulrs";
import { fetch_chain, fetch_wallet, get_amount } from "../../libs/FetchApi";
import { ChainType } from "../../types/blockchain";

const index = () => {
  // Wallet information
  const [privateKey, setPrivateKey] = useState<string>("");
  const [publicKey, setPublicKey] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [ownAmount, setOwnAmount] = useState<string>("");

  // Chain Information
  const [chain, setChain] = useState<ChainType>();

  useEffect(() => {
    // ウォレット情報の取得
    fetch_wallet(wallet_url).then((chain) => {
      setPublicKey(chain.public_key);
      setAddress(chain.wallet_address);
      setPrivateKey(chain.private_key);
      setOwnAmount(chain.amount);
    });

    handleGetChain();
  }, []);

  const handleGetChain = async () => {
    const chain = await fetch_chain(chain_url);
    setChain(chain);
  };

  const reload_amount = async () => {
    const amount = await get_amount(address, amount_url);
    setOwnAmount(amount);
    alert(`残高の更新をしました[残高=${amount}]`);
  };

  const handleMining = async () => {
    const mining_url = "http://localhost:8080/api/mining";

    const response = await fetch(mining_url, {
      method: "GET",
    });

    const data = await response.json();

    alert("マイニングが完了しました。");
  };

  return (
    <div className="section">
      <WalletInformation
        publicKey={publicKey}
        privateKey={privateKey}
        address={address}
        amount={ownAmount}
      />

      <SendMoney
        publicKey={publicKey}
        privateKey={privateKey}
        address={address}
      />

      {/* Action */}
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

      {/* ブロックチェーン情報 */}
      <BlockChainInformation chain={chain} />

      <Footer />
    </div>
  );
};

export default index;
