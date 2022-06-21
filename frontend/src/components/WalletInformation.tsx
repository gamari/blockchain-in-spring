import React from "react";

type Props = {
  publicKey: string;
  privateKey: string;
  address: string;
  amount: string;
};

const WalletInformation: React.FC<Props> = ({
  publicKey,
  privateKey,
  address,
  amount,
}) => {
  return (
    <div className="flex flex-col">
      <h1 className="section-title">ウォレット情報</h1>
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
            {amount ? <>{amount}</> : <>なし</>}
          </p>
        </div>
      </div>
    </div>
  );
};

export default WalletInformation;
