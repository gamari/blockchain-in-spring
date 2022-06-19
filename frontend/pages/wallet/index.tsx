import React, { useEffect, useState } from "react";

const index = () => {
  const [privateKey, setPrivateKey] = useState<string>("");
  const [publicKey, setPublicKey] = useState<string>("");
  const [address, setAddress] = useState<string>("");

  // SendMoneyInformation
  const [recipientAddress, setRecipientAddress] = useState<string>("");
  const [amount, setAmount] = useState<string>("");

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

  return (
    <div className="flex flex-col justify-center items-center container mx-auto">
      {/* ウォレット情報 */}
      <div className="flex flex-col">
        <h1 className="font-bold text-4xl my-6">Wallet Information</h1>

        <div>
          <h1>Your PrivateKey</h1>
          <p>{privateKey}</p>
        </div>

        <div>
          <h1>Your PublicKey</h1>
          <p>{publicKey}</p>
        </div>

        <div>
          <h1>Wallet Address</h1>
          <p>{address}</p>
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
    </div>
  );
};

export default index;
