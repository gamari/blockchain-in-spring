import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { blockchain_atom } from "../../atoms/BlockChainAtoms";
import { wallet_amount_atom } from "../../atoms/WalletAtoms";
import ActionBox from "../../components/ActionBox";
import BlockChainInformation from "../../components/BlockChainInformation";
import Footer from "../../components/Footer";
import SendMoney from "../../components/SendMoney";
import WalletInformation from "../../components/WalletInformation";
import { chain_url, wallet_url } from "../../constants/ulrs";
import { fetch_chain, fetch_wallet } from "../../libs/FetchApi";

const uid = () => {
  // Wallet information
  const [privateKey, setPrivateKey] = useState<string>("");
  const [publicKey, setPublicKey] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [amount, setAmount] = useRecoilState(wallet_amount_atom);

  // Chain Information
  const [chain, setChain] = useRecoilState(blockchain_atom);

  useEffect(() => {
    handleGetWallet();
    handleGetChain();
  }, []);

  const handleGetWallet = async () => {
    // ウォレット情報の取得
    const wallet = await fetch_wallet(wallet_url);

    setPublicKey(wallet.public_key);
    setAddress(wallet.wallet_address);
    setPrivateKey(wallet.private_key);
    setAmount(wallet.amount);
  };

  const handleGetChain = async () => {
    const chain = await fetch_chain(chain_url);
    setChain(chain);
  };

  return (
    <div className="section">
      <WalletInformation
        publicKey={publicKey}
        privateKey={privateKey}
        address={address}
        amount={amount}
      />
      <SendMoney
        publicKey={publicKey}
        privateKey={privateKey}
        address={address}
      />
      <ActionBox address={address} />
      <BlockChainInformation chain={chain} />

      <Footer />
    </div>
  );
};

export default uid;
