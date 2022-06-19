import React from "react";

type Props = {
  previous_hash?: string;
  nonce?: string;
  transactions: [any];
};

const BlockComponent: React.FC<Props> = ({
  previous_hash,
  nonce,
  transactions,
}) => {
  return (
    <div className=" border-2 border-black p-6 w-72">
      <div>
        <h3 className="font-bold text-xl">Previous Hash</h3>
        <p>{previous_hash}</p>
      </div>

      <div>
        <h3 className="font-bold text-xl">Nonce</h3>
        <p>{nonce}</p>
      </div>

      <div>
        <h3 className="font-bold text-xl">Transaction情報</h3>
        <div>
          <div>
            {/* TODO component化 */}
            {transactions && (
              <div className="p-2">
                <div>[Recipient]</div>
                <div>{transactions[0]?.recipientAddress}</div>
                <div>[Sender]</div>
                <div>{transactions[0]?.senderAddress}</div>
                <div>[Value]</div>
                <div>{transactions[0]?.value}</div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default BlockComponent;
