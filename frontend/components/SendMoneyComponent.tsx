import React, { useState } from "react";

type Props = {
  publicKey: string;
  privateKey: string;
  address: string;
};

const SendMoneyComponent: React.FC<Props> = ({
  publicKey,
  privateKey,
  address,
}) => {
  const [recipientAddress, setRecipientAddress] = useState<string>("");
  const [amount, setAmount] = useState<string>("");

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
  );
};

export default SendMoneyComponent;
