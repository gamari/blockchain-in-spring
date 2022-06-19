import React, { useEffect, useState } from "react";

const index = () => {
  // Wallet information
  const [privateKey, setPrivateKey] = useState<string>("");
  const [publicKey, setPublicKey] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [ownAmount, setOwnAmount] = useState<string>("");

  // SendMoney Information
  const [recipientAddress, setRecipientAddress] = useState<string>("");
  const [amount, setAmount] = useState<string>("");

  // Mining Infromation

  useEffect(() => {
    const fetchUrl = "http://localhost:8080/api/wallet";
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
  }, []);

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
        alert("マイニングしました。");
      });
  };

  return (
    <div className="flex flex-col justify-center items-center container mx-auto">
      {/* ウォレット情報 */}
      <div className="flex flex-col">
        <h1 className="font-bold text-4xl my-6">Wallet Information</h1>

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
            <button
              className="border-2 border-gray-300"
              onClick={() => {
                reload_amount();
              }}
            >
              更新
            </button>
          </div>
        </div>
      </div>

      {/* 送金 */}
      <div className="flex flex-col">
        <h1>送金情報</h1>

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

      {/* Mining情報 */}
      <div>
        <h1>マイニング情報</h1>
        <div>
          <button
            className="border-2 border-gray-500"
            onClick={() => {
              handleMining();
            }}
          >
            手動でマイニング
          </button>
        </div>
      </div>
    </div>
  );
};

export default index;
