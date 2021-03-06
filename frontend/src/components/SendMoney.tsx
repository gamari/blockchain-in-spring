import React, { useState } from "react";
import { send_money_url } from "../constants/ulrs";

type Props = {
  publicKey: string;
  privateKey: string;
  address: string;
};

const SendMoney: React.FC<Props> = ({ publicKey, privateKey, address }) => {
  const [recipientAddress, setRecipientAddress] = useState<string>("");
  const [amount, setAmount] = useState<string>("");

  const handleSendMoney = async () => {
    const transaction_data = {
      senderPublicKey: publicKey,
      senderPrivateKey: privateKey,
      senderBlockchainAddress: address,
      recipientBlockchainAddress: recipientAddress,
      value: amount,
    };
    const response = await fetch(send_money_url, {
      method: "POST",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(transaction_data),
    });

    if (response.status == 400) {
      const json = await response.json();
      alert(json["message"]);
      return;
    }

    if (response.status != 201) {
      alert("エラーが発生しました。");
      return;
    }

    const json = await response.json();
    alert("送金が完了しました。");
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

export default SendMoney;
