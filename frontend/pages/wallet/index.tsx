import React, { useEffect, useState } from "react";
import BlockChainInformation from "../../components/BlockChainInformation";
import SendMoneyComponent from "../../components/SendMoneyComponent";
import WalletInformation from "../../components/WalletInformation";

const index = () => {
  // Wallet information
  const [privateKey, setPrivateKey] = useState<string>("");
  const [publicKey, setPublicKey] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [ownAmount, setOwnAmount] = useState<string>("");

  // Chain Information
  const [chain, setChain] = useState<[any]>();

  useEffect(() => {
    const fetchUrl = "http://localhost:8080/api/wallet";

    // ウォレット情報を取得
    fetch(fetchUrl, {
      method: "POST",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        setPrivateKey(data["privateKey"]);
        setPublicKey(data["publicKey"]);
        setAddress(data["blockchainAddress"]);
      });

    handleGetChain();
  }, []);

  const handleGetChain = () => {
    // ブロックチェーン情報を取得
    const fetchChain = "http://localhost:8080/api/chain";
    fetch(fetchChain, {
      method: "GET",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        console.log(res);
        return res.json();
      })
      .then((data) => {
        console.log(data);
        setChain(data["chain"]);
      });
  };

  const reload_amount = () => {
    const reload_url = "http://localhost:8080/api/amount?";
    const params = {
      address: address,
    };
    const query_params = new URLSearchParams(params);
    fetch(reload_url + query_params, {
      method: "GET",
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        console.log(data);
        alert(`残高の更新をしました[残高=${data["amount"]}]`);
        setOwnAmount(data["amount"]);
      });
  };

  const handleMining = () => {
    const mining_url = "http://localhost:8080/api/mining";

    fetch(mining_url, {
      method: "GET",
    })
      .then((res) => {
        console.log(res);
      })
      .then((data) => {
        alert("マイニングが完了しました。");
      });
  };

  return (
    <div className="section">
      {/* ウォレット情報 */}
      <div className="flex flex-col">
        <h1 className="section-title">ウォレット情報</h1>

        {/* Information */}
        <WalletInformation
          publicKey={publicKey}
          privateKey={privateKey}
          address={address}
          amount={ownAmount}
        />
      </div>

      {/* 送金情報 */}
      <SendMoneyComponent
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
              reload_amount();
            }}
          >
            残高を更新する
          </button>
          <button
            className="btn btn-gray"
            onClick={() => {
              handleMining();
            }}
          >
            手動でマイニング
          </button>

          <button
            className="btn btn-gray"
            onClick={() => {
              handleGetChain();
            }}
          >
            ブロック情報を更新
          </button>
        </p>
      </div>

      {/* ブロックチェーン情報 */}
      <BlockChainInformation chain={chain} />

      <footer className="h-24 bg-gray-100 w-[100vw] flex justify-center items-center">
        <div>footer</div>
      </footer>
    </div>
  );
};

export default index;
