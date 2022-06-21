import React from "react";
import Block from "./Block";

type Props = {
  chain?: [any];
};

const BlockChainInformation: React.FC<Props> = ({ chain }) => {
  return (
    <div className="section mt-10">
      <h1 className="section-title">ブロックチェーン情報</h1>
      <p>ブロックチェーンの情報を表示します。</p>
      <div className="flex flex-row break-words space-x-4 mt-10">
        {chain?.map((block: any, i) => {
          return (
            <div key={i}>
              <div>block{i}</div>
              <Block
                previous_hash={block.previousHash}
                nonce={block.nonce}
                transactions={block.transactions}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default BlockChainInformation;
