package org.ergoplatform.explorer.grabber.modules

import cats.effect.Sync
import fs2.Stream
import org.ergoplatform.explorer.db.algebra.LiftConnectionIO
import org.ergoplatform.explorer.db.repositories._
import tofu.syntax.monadic._

final case class RepoBundle[D[_]](
  headers: HeaderRepo[D],
  blocksInfo: BlockInfoRepo[D],
  blockExtensions: BlockExtensionRepo[D],
  adProofs: AdProofRepo[D],
  txs: TransactionRepo[D, Stream],
  inputs: InputRepo[D],
  dataInputs: DataInputRepo[D],
  outputs: OutputRepo[D, Stream],
  assets: AssetRepo[D, Stream],
  registers: BoxRegisterRepo[D],
  tokens: TokenRepo[D]
)

object RepoBundle {

  def apply[F[_]: Sync, D[_]: LiftConnectionIO]: F[RepoBundle[D]] =
    (
      HeaderRepo[F, D],
      BlockInfoRepo[F, D],
      BlockExtensionRepo[F, D],
      AdProofRepo[F, D],
      TransactionRepo[F, D],
      InputRepo[F, D],
      DataInputRepo[F, D],
      OutputRepo[F, D],
      AssetRepo[F, D],
      BoxRegisterRepo[F, D],
      TokenRepo[F, D]
    ).mapN(RepoBundle(_, _, _, _, _, _, _, _, _, _, _))
}