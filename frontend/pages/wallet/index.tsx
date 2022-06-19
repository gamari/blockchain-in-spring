import React, { useEffect, useState } from "react";
import BlockComponent from "../../components/BlockComponent";

const index = () => {
  // Wallet information
  const [privateKey, setPrivateKey] = useState<string>("");
  const [publicKey, setPublicKey] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [ownAmount, setOwnAmount] = useState<string>("");

  // SendMoney Information
  const [recipientAddress, setRecipientAddress] = useState<string>("");
  const [amount, setAmount] = useState<string>("");

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

  const handleSendMoney = () => {
    const sendMoneyUrl = "http://localhost:8080/api/transaction";

    const transaction_data = {
      senderPublicKey: publicKey,
      senderPrivateKey: privateKey,
      senderBlockchainAddress: address,
      recipientBlockchainAddress: recipientAddress,
      value: amount,
    };

    fetch(sendMoneyUrl, {
      method: "POST",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(transaction_data),
    })
      .then((res) => {
        if (res.status == 400) {
          throw Error("error");
        }
        return res.json();
      })
      .then((data) => {
        alert("送金処理が完了しました。");
      })
      .catch((e) => {
        alert("失敗しました。");
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
        <h1 className="section-title">Wallet Information</h1>

        {/* Information */}
        <div className="flex flex-col p-4 space-y-6 mx-auto max-w-6xl break-words">
          <div>
            <h1 className="font-bold text-2xl">Your PrivateKey</h1>
            <p className="border-[1px] border-gray-500 p-2">{privateKey}</p>
          </div>

          <div>
            <h1 className="font-bold text-2xl">Your PublicKey</h1>
            <p className="border-[1px] border-gray-500 p-2">{publicKey}</p>
          </div>

          <div>
            <h1 className="font-bold text-2xl">Wallet Address</h1>
            <p className="border-[1px] border-gray-500 p-2">{address}</p>
          </div>

          <div>
            <h1 className="font-bold text-2xl">残高</h1>
            <p className="border-[1px] border-gray-500 p-2">
              {ownAmount ? <>{ownAmount}</> : <>なし</>}
            </p>
          </div>

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
        </div>
      </div>

      {/* 送金情報 */}
      <div className="section mt-10">
        <h1 className="section-title">送金情報</h1>

        <div className="">
          <div>
            <h2>送金先</h2>
            <input
              type="text"
              className="max-w-4xl border-2 border-black w-full"
              value={recipientAddress}
              onChange={(e) => {
                setRecipientAddress(e.target.value);
              }}
            />
          </div>

          <div className="my-5">
            <h2 className="">金額</h2>
            <input
              type="text"
              className="max-w-4xl border-2 border-black"
              value={amount}
              onChange={(e) => {
                setAmount(e.target.value);
              }}
            />
          </div>

          <button
            className="border-2 border-gray-500 hover:bg-gray-600 hover:text-white"
            onClick={() => {
              handleSendMoney();
            }}
          >
            送金する
          </button>
        </div>
      </div>

      {/* ブロックチェーン情報 */}
      <div className="section mt-10">
        <h1 className="section-title">ブロックチェーン情報</h1>
        <p>ブロックチェーンの情報を表示します。</p>
        <div className="flex flex-row break-words space-x-4 mt-10">
          {chain?.map((block: any, i) => {
            return (
              <div key={i}>
                <div>block{i}</div>
                <BlockComponent
                  previous_hash={block.previousHash}
                  nonce={block.nonce}
                  transactions={block.transactions}
                />
              </div>
            );
          })}
        </div>
      </div>

      <footer className="h-24 bg-gray-100 w-[100vw] flex justify-center items-center">
        <div>footer</div>
      </footer>
    </div>
  );
};

export default index;
